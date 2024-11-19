import React, { useState, useEffect } from 'react';
import estilos from "./Cadastro.module.css";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function Cadastro() {
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        confirmarSenha: '',
        tipo: ''
    });
    const [usuarios, setUsuarios] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchUsuarios() {
            try {
                const response = await axios.get('http://localhost:8080/usuarios');
                setUsuarios(response.data);
            } catch (error) {
                console.error('Erro ao buscar usuários:', error);
            }
        }
        fetchUsuarios();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        const parsedValue = name === 'tipo' ? parseInt(value) : value; // converte para inteiro apenas para o campo 'tipo'
        setFormData({
            ...formData,
            [name]: parsedValue
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (formData.senha !== formData.confirmarSenha) {
            alert('As senhas não coincidem.');
            return;
        }

        if (usuarios.some(user => user.email === formData.email)) {
            alert('Este email já está em uso.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/usuarios', {
                nome: formData.nome,
                login: formData.nome,
                email: formData.email,
                senha: formData.senha,
                tipo: formData.tipo // será 1, 2 ou 3 conforme selecionado
            });
            alert('Usuário criado com sucesso!');
            navigate('/');
            console.log(response.data);
        } catch (error) {
            console.error('Erro ao criar usuário:', error);
            alert('Erro ao criar usuário.');
        }
    };

    return (
        <div className={estilos.principal}>
            <h1>Crie uma conta!</h1>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text" 
                    name="nome" 
                    placeholder="Nome" 
                    value={formData.nome} 
                    onChange={handleChange} 
                />
                <input 
                    type="email" 
                    name="email" 
                    placeholder="Email" 
                    value={formData.email} 
                    onChange={handleChange} 
                />
                <input 
                    type="password" 
                    name="senha" 
                    placeholder="Senha" 
                    value={formData.senha} 
                    onChange={handleChange} 
                />
                <input 
                    type="password" 
                    name="confirmarSenha" 
                    placeholder="Confirme a senha" 
                    value={formData.confirmarSenha} 
                    onChange={handleChange} 
                />
                <select className={estilos.select}
                    name="tipo"
                    value={formData.tipo}
                    onChange={handleChange}
                >
                    <option value="">Selecione seu tipo de usuário</option>
                    <option value="1">Aluno</option>
                    <option value="2">Professor</option>
                    <option value="3">Coordenador</option>
                </select>
                <button 
                    className={estilos.botaoCadastro} 
                    type="submit"
                >
                    Cadastrar-se
                </button>
            </form>
            <div className={estilos.linksCadastro}>
                <a href="/">Já possui uma conta? Faça login!</a>
                <a href="/EsqueciMinhaSenha">Esqueceu sua senha?</a>
            </div>
        </div>
    );
}

export default Cadastro;
