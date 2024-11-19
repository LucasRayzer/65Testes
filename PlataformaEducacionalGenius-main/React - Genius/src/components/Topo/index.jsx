import { AiOutlineBell } from "react-icons/ai";
import { AiOutlineUser } from "react-icons/ai";
import estilos from "./Topo.module.css"
import { useState } from "react";
import { AiOutlineSetting } from "react-icons/ai";

function Topo() {

    const [opcoes, setOpcoes] = useState(false);

    function mostrarOpcoes(){
        setOpcoes(!opcoes);
    };

    return (
        <header>
            <div className={estilos.logo}>
                <div className={estilos.logoImagem}>
                    <a href="/Cursos"><img src="/logo.svg"></img></a>
                </div>
                <div className={estilos.logoDescricao}>
                    <p>GENIUS</p>
                </div>
            </div>
            <nav className={estilos.linksTopo}>
                <a href="/Cursos">Meus cursos</a>
            </nav>
            <div className={estilos.perfil}>
                <a><AiOutlineBell size={25}/></a>
                <a onClick={mostrarOpcoes}><AiOutlineUser size={27}/></a>
                {opcoes && (
                    <div className={estilos.menu}>
                        <ul>
                            <a className={estilos.linksMenu} href="/Perfil">Meu Perfil</a>
                            <a className={estilos.linksMenu} href="/">Sair</a>
                        </ul>
                    </div>
                )}
            </div>
        </header>
    )
}

export default Topo
