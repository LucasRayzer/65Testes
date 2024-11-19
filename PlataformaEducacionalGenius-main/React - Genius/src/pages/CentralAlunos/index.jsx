import React, { useState, useEffect } from "react";
import axios from "axios";
import Footer from "../../components/Footer";
import TopoAdmin from "../../components/TopoAdmin";
import estilos from "./CentralAlunos.module.css";
import { AiOutlineClose } from "react-icons/ai";

function CentralAlunos() {
  const [alunos, setAlunos] = useState([]);
  const [idAluno, setIdAluno] = useState("");
  const [idCurso, setIdCurso] = useState("");

  useEffect(() => {
    fetchAlunos();
  }, []);

  const fetchAlunos = async () => {
    try {
      const response = await axios.get("http://localhost:8080/usuarios");
      const alunosFiltrados = response.data.filter(usuario => usuario.tipo === 2);
      setAlunos(alunosFiltrados);
    } catch (error) {
      console.error("Erro ao buscar alunos:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/usuarios/${idAluno}/cursos/${idCurso}`);
      alert("Aluno atribuído ao curso com sucesso!");
      setIdAluno("");
      setIdCurso("");
    } catch (error) {
      console.error("Erro ao atribuir aluno ao curso:", error);
      alert("Erro ao atribuir aluno ao curso.");
    }
  };

  const handleExcluirAluno = async (idAluno) => {
    const confirmarExclusao = window.confirm("Tem certeza que deseja excluir este aluno?");
    
    if (confirmarExclusao) {
      try {
        await axios.delete(`http://localhost:8080/usuarios/${idAluno}`);
        setAlunos(alunos.filter(aluno => aluno.idUsuario !== idAluno));
        alert("Aluno excluído com sucesso!");
      } catch (error) {
        console.error("Erro ao excluir aluno:", error);
        alert("Erro ao excluir aluno.");
      }
    }
  };

  return (
    <div>
      <TopoAdmin />
      <div className={estilos.containerCentralAlunos}>
        <div className={estilos.centralAlunos}>
          <h3>Alunos</h3>
          <div className={estilos.mostrarAlunos}>
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
                {alunos.map((aluno) => (
                  <tr key={aluno.idUsuario}>
                    <td>{aluno.idUsuario}</td>
                    <td>{aluno.nome}</td>
                    <td>{aluno.email}</td>
                    <td>
                      <a href="#" onClick={() => handleExcluirAluno(aluno.idUsuario)}>
                        <AiOutlineClose />
                      </a>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <h3>Atribuir Aluno a Curso</h3>
          <div className={estilos.atribuirAlunos}>
            <form className={estilos.formAlunos} onSubmit={handleSubmit}>
              <input
                type="text"
                name="idAluno"
                placeholder=" ID do aluno"
                value={idAluno}
                onChange={(e) => setIdAluno(e.target.value)}
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

export default CentralAlunos;
