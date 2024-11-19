import estilos from "./Login.module.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';

function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [loginFalhou, setLoginFalhou] = useState(false);
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

    function emailHandler(evento) {
        setEmail(evento.target.value);
    }

    function senhaHandler(evento) {
        setSenha(evento.target.value);
    }

    function loginHandler() {
        const usuarioEncontrado = usuarios.find(user => user.email === email && user.senha === senha);
        if (usuarioEncontrado) {
            setLoginFalhou(false);
            if (usuarioEncontrado.tipo === 3) {
                navigate('/CentralCursos');
            } else {
                navigate('/Cursos', { state: { usuario: usuarioEncontrado } });
            }
        } else {
            setLoginFalhou(true);
        }
    }

    return (
        <div className={estilos.principalLogin}>
            <div className={estilos.logo}>
                <img src="/imagem3.png" alt="logo" />
                <h1>Genius</h1>
            </div>
            <div>
                <label>Email:</label>
                <input type="email" name="email" value={email} onChange={emailHandler} />
                <label>Senha:</label>
                <input type="password" name="senha" value={senha} onChange={senhaHandler} />
                <button className={estilos.botaoLogin} type="button" onClick={loginHandler}>ENTRAR</button>
            </div>
            <div className={estilos.falhou}>
                {loginFalhou && <div className="FalhouMgs"> <p>Email ou senha incorretos!</p> </div>}
            </div>
            <div className={estilos.linksLogin}>
                <a href="/Cadastro">Cadastrar-se</a>
                <a href="/EsqueciMinhaSenha">Esqueceu sua senha?</a>
            </div>
        </div>
    )
}

export default Login;
