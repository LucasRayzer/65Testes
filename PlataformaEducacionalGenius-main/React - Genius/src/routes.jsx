import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Cadastro from "./pages/Cadastro";
import EsqueciMinhaSenha from "./pages/EsqueciMinhaSenha";
import Cursos from "./pages/Cursos";
import Perfil from "./pages/Perfil";
import Disciplinas from "./pages/Disciplinas";
import CentralCursos from "./pages/CentralCursos";
import CentralDisciplinas from "./pages/CentralDisciplinas";
import CentralProfessores from "./pages/CentralProfessores";
import CentralAlunos from "./pages/CentralAlunos";
import AreaDisciplina from "./pages/AreaDisciplina";
import ControleDisciplina from "./pages/ControleDisciplina";


function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>}></Route>
                <Route path="/Cadastro" element={<Cadastro/>}></Route>
                <Route path="/EsqueciMinhaSenha" element={<EsqueciMinhaSenha/>}></Route>
                <Route path="/Cursos" element={<Cursos/>}></Route>
                <Route path="/Disciplinas/:idCurso" element={<Disciplinas/>}></Route>
                <Route path="/Perfil" element={<Perfil/>}></Route>
                <Route path="/CentralCursos" element={<CentralCursos/>}></Route>
                <Route path="/CentralDisciplina" element={<CentralDisciplinas/>}></Route>
                <Route path="/CentralProfessores" element={<CentralProfessores/>}></Route>
                <Route path="/CentralAlunos" element={<CentralAlunos/>}></Route>
                <Route path="/AreaDisciplina/:idDisciplina" element={<AreaDisciplina/>}></Route>
                <Route path="/ControleDisciplina/:idDisciplina" element={<ControleDisciplina/>}></Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AppRoutes