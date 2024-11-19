import estilos from "./Secao.module.css"

function Secao({titulo, descricao}){
    return(
        <div>
            <div className={estilos.containerSecao}>
                <div className={estilos.tituloSecao}>
                    <h3>{titulo}</h3>
                </div>
                <div className={estilos.descricaoSecao}>
                    <p>{descricao}</p>
                </div>
            </div>
        </div>
    )
}

export default Secao