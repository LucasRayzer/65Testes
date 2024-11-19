import estilos from "./TopoAdmin.module.css"
import { AiOutlineLogout } from "react-icons/ai";
import { useState } from "react";


function TopoAdmin() {

    const [opcoes, setOpcoes] = useState(false);

    function mostrarOpcoes(){
        setOpcoes(!opcoes);
    };
    return (

        <header>
            <div className={estilos.logo}>
                <div className={estilos.logoImagem}>
                    <a href="/CentralCursos"><img src="/logo.svg"></img></a>
                </div>
                <div className={estilos.logoDescricao}>
                    <p>GENIUS</p>
                </div>
            </div>
            <nav className={estilos.linksTopoAdmin}>
                <a href="/CentralCursos">Cursos</a>
                <a href="/CentralDisciplina">Disciplinas</a>
                <a href="/CentralProfessores">Professores</a>
                <a href="/CentralAlunos">Alunos</a>
            </nav>
            <div className={estilos.perfil}>
                <a onClick={mostrarOpcoes}><AiOutlineLogout size={27}/></a>
                {opcoes && (
                    <div className={estilos.menu}>
                        <ul>
                            <a className={estilos.linksMenu} href="/">Sair</a>
                        </ul>
                    </div>
                )}
            </div>
        </header>
    )
}

export default TopoAdmin
