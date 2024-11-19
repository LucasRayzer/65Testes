import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useLocation } from 'react-router-dom';
import CardCurso from '../../components/CardCurso';
import Footer from '../../components/Footer';
import Topo from '../../components/Topo';
import estilos from './Cursos.module.css';

function Cursos() {
    const location = useLocation();
    const usuario = location.state?.usuario;

    const [cursos, setCursos] = useState([]);

    useEffect(() => {
        if (usuario) {
            axios.get(`http://localhost:8080/usuarios/${usuario.idUsuario}/cursos`)
                .then(response => {
                    setCursos(response.data);
                })
                .catch(error => {
                    console.error('Erro ao carregar cursos:', error);
                });
        }
    }, [usuario]);

    return (
        <div className={estilos.container}>
            <Topo />
            <div className={estilos.areaPrincipalCursos}>
                <div className={estilos.cardsCursos}>
                    {cursos.map(curso => (
                        <Link key={curso.idCurso} to={`/Disciplinas/${curso.idCurso}`}>
                            <CardCurso titulo={curso.titulo} />
                        </Link>
                    ))}
                </div>
            </div>
            <div className={estilos.footer}>
                <Footer />
            </div>
        </div>
    );
}

export default Cursos;
