import React, { useState, useEffect } from "react";
import axios from "axios";
import Footer from "../../components/Footer";
import TopoAdmin from "../../components/TopoAdmin";
import estilos from "./CentralDisciplinas.module.css";
import { AiOutlineEdit, AiOutlineClose } from "react-icons/ai";

function CentralDisciplinas() {
  const [cursoIdBusca, setCursoIdBusca] = useState("");
  const [curso, setCurso] = useState("");
  const [titulo, setTitulo] = useState("");
  const [descricao, setDescricao] = useState("");
  const [alerta, setAlerta] = useState("");
  const [disciplinas, setDisciplinas] = useState([]);
  const [editingDisciplina, setEditingDisciplina] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!titulo || !descricao) {
      setAlerta("Todos os campos devem ser preenchidos!");
      return;
    }

    try {
      if (editingDisciplina) {
        await axios.put(
          `http://localhost:8080/disciplinas/${editingDisciplina.idDisciplina}`,
          {
            idCurso: curso,
            titulo,
            descricao,
          }
        );
        alert("Disciplina editada com sucesso!");
      } else {
        await axios.post(`http://localhost:8080/cursos/${curso}/disciplinas`, {
          titulo,
          descricao,
        });
        alert("Disciplina criada com sucesso!");
      }
      setCurso("");
      setTitulo("");
      setDescricao("");
      setEditingDisciplina(null);
      fetchDisciplinas(cursoIdBusca);
    } catch (error) {
      console.error("Erro ao salvar a disciplina:", error);
      alert("Erro ao salvar a disciplina. Verifique os dados e tente novamente.");
    }
  };

  const handleBuscar = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/cursos/${cursoIdBusca}/disciplinas`
      );
      setDisciplinas(response.data);
    } catch (error) {
      console.error("Erro ao buscar disciplinas do curso:", error);
      alert(
        "Erro ao buscar disciplinas do curso. Verifique o ID e tente novamente."
      );
      setDisciplinas([]);
    }
  };

  const fetchDisciplinas = async (cursoId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/cursos/${cursoId}/disciplinas`
      );
      setDisciplinas(response.data);
    } catch (error) {
      setDisciplinas([]);
    }
  };

  const handleExcluirDisciplina = async (idDisciplina) => {
    const confirmarExclusao = window.confirm(
      "Tem certeza que deseja excluir esta disciplina?"
    );
    if (confirmarExclusao) {
      try {
        await axios.delete(`http://localhost:8080/disciplinas/${idDisciplina}`);
        setDisciplinas(
          disciplinas.filter(
            (disciplina) => disciplina.idDisciplina !== idDisciplina
          )
        );
        alert("Disciplina excluída com sucesso!");
      } catch (error) {
        console.error("Erro ao excluir disciplina:", error);
        alert(
          "Erro ao excluir disciplina. Tente novamente mais tarde."
        );
      }
    }
  };

  const handleEdit = (disciplina) => {
    setEditingDisciplina(disciplina);
    setCurso(disciplina.idCurso);
    setTitulo(disciplina.titulo);
    setDescricao(disciplina.descricao);
  };

  return (
    <div>
      <TopoAdmin />
      <div className={estilos.containerCentralDisciplinas}>
        <div className={estilos.centralDisciplinas}>
          <h3>{editingDisciplina ? "Editar Disciplina" : "Criar Disciplina"}</h3>
          {alerta && <div className={estilos.alerta}>{alerta}</div>}
          <div className={estilos.criarDisciplina}>
            <form className={estilos.formDisciplinas} onSubmit={handleSubmit}>
              {!editingDisciplina && (
                <input
                  type="text"
                  name="curso"
                  placeholder="ID do curso"
                  value={curso}
                  onChange={(e) => setCurso(e.target.value)}
                />
              )}
              <input
                type="text"
                name="titulo"
                placeholder="Título"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
              />
              <input
                type="text"
                name="descricao"
                placeholder="Descrição"
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
              />
              <button className={estilos.botaoCriar} type="submit">
                {editingDisciplina ? "Salvar" : "Criar"}
              </button>
              {editingDisciplina && (
                <button
                  type="button"
                  className={estilos.botaoCancelar}
                  onClick={() => {
                    setEditingDisciplina(null);
                    setCurso("");
                    setTitulo("");
                    setDescricao("");
                  }}
                >
                  Cancelar
                </button>
              )}
            </form>
          </div>
          <h3>Buscar Disciplinas por Curso</h3>
          <input
            className={estilos.busca}
            type="text"
            placeholder="ID do curso"
            value={cursoIdBusca}
            onChange={(e) => setCursoIdBusca(e.target.value)}
          />
          <button className={estilos.botaoBuscar} onClick={handleBuscar}>
            Buscar
          </button>
          <div className={estilos.mostrarDisciplina}>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Título</th>
                  <th>Descrição</th>
                  <th>Opções</th>
                </tr>
              </thead>
              <tbody>
                {disciplinas.map((disciplina) => (
                  <tr key={disciplina.idDisciplina}>
                    <td>{disciplina.idDisciplina}</td>
                    <td>{disciplina.titulo}</td>
                    <td>{disciplina.descricao}</td>
                    <td>
                      <a
                        href="#"
                        onClick={(e) => {
                          e.preventDefault();
                          handleExcluirDisciplina(disciplina.idDisciplina);
                        }}
                      >
                        <AiOutlineClose />
                      </a>{" "}
                      <a
                        href="#"
                        onClick={(e) => {
                          e.preventDefault();
                          handleEdit(disciplina);
                        }}
                      >
                        <AiOutlineEdit />
                      </a>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default CentralDisciplinas;
