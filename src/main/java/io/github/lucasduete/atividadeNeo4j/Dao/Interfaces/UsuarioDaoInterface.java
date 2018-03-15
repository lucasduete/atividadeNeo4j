package io.github.lucasduete.atividadeNeo4j.Dao.Interfaces;

import io.github.lucasduete.atividadeNeo4j.Model.Usuario;

import java.util.List;

public interface UsuarioDaoInterface {

    public boolean cadastrar(Usuario usuario);
    public List<Usuario> listar();
    public List<Usuario> listarSeguidores(String emailUser);
    public List<Usuario> listarSeguindo(String emailUser);
    public boolean seguir(String emailUser, String emailDestiny);
    public boolean remover(Usuario usuario);
    public boolean atualizar(Usuario usuario);
}
