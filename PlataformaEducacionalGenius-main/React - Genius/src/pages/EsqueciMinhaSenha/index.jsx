import estilos from "./EsqueciMinhaSenha.module.css"

function EsqueciMinhaSenha(){
    return(
        <div className={estilos.containerPrincipal}>
            <div className={estilos.descricao}>
                <h1>Esqueceu sua senha?</h1>
                <p>Vamos resolver isso!</p>
                <p>Digite seu email que enviaremos um link para que você possa redefinir sua senha!</p>
            </div>
            <form className= {estilos.formulario} action="">
                <input type="email" name="email" placeholder=" Email"/>
                <button className={estilos.botaoEsqueci}>ENVIAR EMAIL!</button>
            </form>
            <div className={estilos.linksEsqueci}>
                <a href="/">Deseja fazer login? Clique aqui!</a>
            </div>
        </div>
    )
}

export default EsqueciMinhaSenha