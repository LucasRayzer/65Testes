import React, { useState } from 'react';
import axios from 'axios';
import Topo from "../../components/Topo";
import estilos from "./ControleDisciplina.module.css";
import { AiOutlineArrowLeft } from "react-icons/ai";
import { useParams, useNavigate } from "react-router-dom";

function ControleDisciplina() {
    const { idDisciplina } = useParams();
    const navigate = useNavigate();
    const [titulo, setTitulo] = useState('');
    const [descricao, setDescricao] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/disciplina/${idDisciplina}/secoes`, {
                titulo,
                descricao
            });
            console.log('Seção adicionada:', response.data);
            alert("Seção criada!");
        } catch (error) {
            console.error('Erro ao adicionar seção:', error);
        }
    };

    return (
        <div>
            <Topo />
            <div className={estilos.containerAreacontroleDisciplina}>
                <div className={estilos.controleDisciplina}>
                    <a href={`/AreaDisciplina/${idDisciplina}`}><AiOutlineArrowLeft /></a>
                    <h1>Adicionar nova seção</h1>
                </div>
                <div className={estilos.containerNovaSecao}>
                    <form className={estilos.novaSecao} onSubmit={handleSubmit}>
                        <input 
                            type="text" 
                            name="titulo" 
                            placeholder="Título" 
                            value={titulo}
                            onChange={(e) => setTitulo(e.target.value)}
                        />
                        <textarea 
                            rows="20" 
                            cols="80" 
                            type="text" 
                            name="descricao" 
                            placeholder="Descrição" 
                            value={descricao}
                            onChange={(e) => setDescricao(e.target.value)}
                        />
                        <button type="submit">Adicionar</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default ControleDisciplina;
