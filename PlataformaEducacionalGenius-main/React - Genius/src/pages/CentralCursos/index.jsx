import React, { useState, useEffect } from "react";
import axios from 'axios';
import Footer from "../../components/Footer";
import TopoAdmin from "../../components/TopoAdmin";
import estilos from "./CentralCursos.module.css";
import { AiOutlineEdit, AiOutlineClose } from "react-icons/ai";

function CentralCursos() {
  const [curso, setCurso] = useState({
    titulo: '',
    descricao: '',
  });
  const [cursos, setCursos] = useState([]);
  const [error, setError] = useState('');
  const [editingCurso, setEditingCurso] = useState(null);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCurso((prevCurso) => ({
      ...prevCurso,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!curso.titulo || !curso.descricao) {
      setError('Título e Descrição são obrigatórios.');
      return;
    }
    setError('');
    try {
      if (editingCurso) {
        await axios.put(`http://localhost:8080/cursos/${editingCurso.idCurso}`, curso);
        console.log('Curso atualizado:', curso);
      } else {
        await axios.post('http://localhost:8080/cursos', curso);
        console.log('Curso adicionado:', curso);
      }
      setCurso({ titulo: '', descricao: '' });
      setEditingCurso(null);
      fetchCursos();
    } catch (error) {
        alert('Erro ao salvar o curso.');
    }
  };

  const fetchCursos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/cursos');
      setCursos(response.data);
    } catch (error) {
      console.error('Erro ao buscar cursos:', error);
    }
  };

  const handleDelete = async (cursoId) => {
    const confirmDelete = window.confirm("Tem certeza que deseja excluir este curso?");
    if (confirmDelete) {
      try {
        await axios.delete(`http://localhost:8080/cursos/${cursoId}`);
        fetchCursos();
      } catch (error) {
        console.error('Erro ao excluir curso:', error);
        alert('Erro ao excluir o curso.');
      }
    }
  };

  const handleEdit = (curso) => {
    setEditingCurso(curso);
    setCurso({ titulo: curso.titulo, descricao: curso.descricao });
  };

  useEffect(() => {
    fetchCursos();
  }, []);

  return (
    <div>
      <TopoAdmin />
      <div className={estilos.containerCentralCursos}>
        <div className={estilos.centralCursos}>
          <h3>{editingCurso ? 'Editar Curso' : 'Criar Curso'}</h3>
          <div className={estilos.criarCurso}>
            <form className={estilos.formCursos} onSubmit={handleSubmit}>
              <input
                type="text"
                name="titulo"
                placeholder="Título"
                value={curso.titulo}
                onChange={handleChange}
              />
              <input
                type="text"
                name="descricao"
                placeholder="Descrição"
                value={curso.descricao}
                onChange={handleChange}
              />
              {error && <p className={estilos.error}>{error}</p>}
              <button type="submit" className={estilos.botaoCriar}>
                {editingCurso ? 'Salvar' : 'Criar'}
              </button>
              {editingCurso && (
                <button
                  type="button"
                  className={estilos.botaoCancelar}
                  onClick={() => {
                    setEditingCurso(null);
                    setCurso({ titulo: '', descricao: '' });
                  }}
                >
                  Cancelar
                </button>
              )}
            </form>
          </div>
          <h3>Cursos</h3>
          <div className={estilos.mostrarCursos}>
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
                {cursos.map((curso) => (
                  <tr key={curso.idCurso}>
                    <td>{curso.idCurso}</td>
                    <td>{curso.titulo}</td>
                    <td>{curso.descricao}</td>
                    <td>
                      <a href="#" onClick={() => handleEdit(curso)}>
                        <AiOutlineEdit />
                      </a>{' '}
                      <a href="#" onClick={() => handleDelete(curso.idCurso)}>
                        <AiOutlineClose />
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

export default CentralCursos;
