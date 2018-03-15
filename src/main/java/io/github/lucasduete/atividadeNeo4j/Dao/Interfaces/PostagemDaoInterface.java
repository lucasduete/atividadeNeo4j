package io.github.lucasduete.atividadeNeo4j.Dao.Interfaces;

import io.github.lucasduete.atividadeNeo4j.Model.Postagem;

import java.util.List;

public interface PostagemDaoInterface {

    public boolean cadastrar(Postagem postagem, String emailUser);
    public List<Postagem> listar();
    public List<Postagem> listarByUser(String emailUser);
    public boolean remover(Postagem postagem);
    public boolean atualizar(Postagem postagem);
}
