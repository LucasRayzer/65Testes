import React, { useState, useEffect } from "react";
import axios from "axios";
import Footer from "../../components/Footer";
import TopoAdmin from "../../components/TopoAdmin";
import estilos from "./CentralProfessores.module.css";
import { AiOutlineClose } from "react-icons/ai";

function CentralProfessores() {
  const [professores, setProfessores] = useState([]);
  const [idUsuario, setIdUsuario] = useState("");
  const [idCurso, setIdCurso] = useState("");

  useEffect(() => {
    fetchProfessores();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/usuarios/${idUsuario}/cursos/${idCurso}`);
      alert("Professor atribuído ao curso com sucesso!");
      setIdUsuario("");
      setIdCurso("");
    } catch (error) {
      console.error("Erro ao atribuir professor ao curso:", error);
      alert("Erro ao atribuir professor ao curso.");
    }
  };

  const fetchProfessores = async () => {
    try {
      const response = await axios.get("http://localhost:8080/usuarios");
      const professoresFiltrados = response.data.filter(usuario => usuario.tipo === 1);
      setProfessores(professoresFiltrados);
    } catch (error) {
      console.error("Erro ao buscar professores:", error);
    }
  };

  const handleExcluirProfessor = async (idProfessor) => {
    const confirmarExclusao = window.confirm("Tem certeza que deseja excluir este professor?");
    
    if (confirmarExclusao) {
      try {
        await axios.delete(`http://localhost:8080/usuarios/${idProfessor}`);
        setProfessores(professores.filter(professor => professor.idUsuario !== idProfessor));
        alert("Professor excluído com sucesso!");
      } catch (error) {
        console.error("Erro ao excluir professor:", error);
        alert("Erro ao excluir professor.");
      }
    }
  };

  return (
    <div>
      <TopoAdmin />
      <div className={estilos.containerCentralProfessores}>
        <div className={estilos.centralProfessores}>
          <h3>Professores</h3>
          <div className={estilos.mostrarProfessores}>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nome</th>
                  <th>Email</th>
                  <th>Opções</th>
                </tr>
              </thead>
              <tbody>
                {professores.map((professor) => (
                  <tr key={professor.idUsuario}>
                    <td>{professor.idUsuario}</td>
                    <td>{professor.nome}</td>
                    <td>{professor.email}</td>
                    <td>
                        <a href="#" onClick={() => handleExcluirProfessor(professor.idUsuario)}>
                            <AiOutlineClose />
                        </a>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <h3>Atribuir Professor a Curso</h3>
          <div className={estilos.atribuirProfessor}>
            <form className={estilos.formProfessores} onSubmit={handleSubmit}>
              <input
                type="text"
                name="idUsuario"
                placeholder=" ID do professor"
                value={idUsuario}
                onChange={(e) => setIdUsuario(e.target.value)}
              />
              <input
                type="text"
                name="idCurso"
                placeholder=" ID do curso"
                value={idCurso}
                onChange={(e) => setIdCurso(e.target.value)}
              />
              <button className={estilos.botaoCriar} type="submit">
                Atribuir
              </button>
            </form>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default CentralProfessores;
