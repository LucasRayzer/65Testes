import Topo from "../../components/Topo";
import estilos from "./Perfil.module.css"
import Footer from "../../components/Footer";

function Perfil({nome, email}){
    return(
        <div>
            <Topo/>
            <div className={estilos.secaoPerfil}>
                <h1 >Olá {nome}!</h1>
                <div className={estilos.editarEmail}>
                <h3>Editar email</h3>
                    <form action="">
                        <input type="email" name="email" placeholder=" Email atual"/>
                        <input type="email" name="email" placeholder=" Novo email"/>
                        <button>Salvar</button>
                    </form>
                </div>
                
                <div className={estilos.editarSenha}>
                    <h3>Alterar senha</h3>
                    <form>
                        <input type="password" name="senha" placeholder=" Senha atual" />
                        <input type="password" name="senha" placeholder=" Nova" />
                        <button>Salvar</button>
                    </form>
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default Perfil