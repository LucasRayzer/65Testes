import { useState, useEffect } from 'react';
import axios from 'axios';
import CardCurso from '../../components/CardCurso';
import Footer from '../../components/Footer';
import Topo from '../../components/Topo';
import estilos from './Disciplinas.module.css';
import { useParams } from 'react-router-dom'; 

function Disciplinas() {
    const { idCurso } = useParams();
    const [disciplinas, setDisciplinas] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/cursos/${idCurso}/disciplinas`)
            .then(response => {
                setDisciplinas(response.data); 
            })
            .catch(error => {
                console.error('Erro ao buscar disciplinas:', error);
            });
    }, [idCurso]); 

    return (
        <div className={estilos.areaDisciplinas}>
            <Topo />
            <div className={estilos.areaPrincipalDisciplinas}>
                <div className={estilos.cardsDisciplinas}>
                    {disciplinas.map(disciplina => (
                        <a key={disciplina.idDisciplina} href={`/AreaDisciplina/${disciplina.idDisciplina}`}>
                            <CardCurso titulo={disciplina.titulo} />
                        </a>
                    ))}
                </div>
            </div>
            <div className={estilos.footer}>
                <Footer />
            </div>
        </div>
    );
}

export default Disciplinas;
