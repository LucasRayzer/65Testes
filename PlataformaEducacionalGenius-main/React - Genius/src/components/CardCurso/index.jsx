import estilos from "./CardCurso.module.css"

function CardCurso({titulo}){
    return(
        <div className={estilos.cardCurso}>
            <p>{titulo}</p>
            <img src="/imagemCurso.png" alt="curso"/>
        </div>
    )
}

export default CardCurso