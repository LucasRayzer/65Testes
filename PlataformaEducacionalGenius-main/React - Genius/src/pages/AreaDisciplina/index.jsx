import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Secao from "../../components/Secao";
import Topo from "../../components/Topo";
import estilos from "./AreaDisciplina.module.css";
import { AiOutlineArrowLeft, AiOutlineSetting } from "react-icons/ai";

function AreaDisciplina() {
    const { idDisciplina } = useParams();
    const [secoes, setSecoes] = useState([]);

    useEffect(() => {
        const fetchSecoes = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/disciplina/${idDisciplina}/secoes`);
                setSecoes(response.data);
            } catch (error) {
                console.error("Erro ao buscar seções:", error);
            }
        };

        fetchSecoes();
    }, [idDisciplina]);

    return (
        <div>
            <Topo />
            <div className={estilos.containerAreaDisciplina}>
                <div className={estilos.disciplina}>
                    <a href={`/disciplinas/${idDisciplina}`}><AiOutlineArrowLeft /></a>
                    <a href={`/ControleDisciplina/${idDisciplina}`}><AiOutlineSetting /></a>
                    <h1>Disciplina</h1>
                </div>
                <div className={estilos.bordaSecoes}>
                    <div className={estilos.secoes}>
                        {secoes.map((secao) => (
                            <Secao key={secao.id} titulo={secao.titulo} descricao={secao.descricao} />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AreaDisciplina;
