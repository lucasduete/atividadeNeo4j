package io.github.lucasduete.atividadeNeo4j.Dao.Neo4j;

import io.github.lucasduete.atividadeNeo4j.Enums.Nodes;
import io.github.lucasduete.atividadeNeo4j.Enums.Relacionamentos;
import io.github.lucasduete.atividadeNeo4j.Dao.Interfaces.UsuarioDaoInterface;
import io.github.lucasduete.atividadeNeo4j.Factory.Conexao;
import io.github.lucasduete.atividadeNeo4j.Model.Usuario;
import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoNeo4j implements UsuarioDaoInterface {

    private final GraphDatabaseService conexao;

    public UsuarioDaoNeo4j() {
        conexao = Conexao.getConexao();
    }

    @Override
    public boolean cadastrar(Usuario usuario) {

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.createNode(Nodes.USUARIO);

            node.setProperty("Email", usuario.getEmail());
            node.setProperty("Nome", usuario.getNome());
            node.setProperty("Senha", usuario.getSenha());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try(Transaction tx = conexao.beginTx()) {

            ResourceIterator<Node> iterator = conexao.findNodes(Nodes.USUARIO);

            while (iterator.hasNext()) {
                Node node = iterator.next();

                Usuario user = new Usuario();
                user.setEmail((String) node.getProperty("Email"));
                user.setNome((String) node.getProperty("Nome"));
                user.setSenha((String) node.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    @Override
    public List<Usuario> listarSeguidores(String emailUser) {
        List<Usuario> usuarios = new ArrayList<>();

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Nodes.USUARIO, "Email", emailUser);

            for (Relationship relacionamento : node.getRelationships(Direction.INCOMING, Relacionamentos.SEGUIR)) {
                Node node2 = relacionamento.getEndNode();

                Usuario user = new Usuario();
                user.setEmail((String) node2.getProperty("Email"));
                user.setNome((String) node2.getProperty("Nome"));
                user.setSenha((String) node2.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    @Override
    public List<Usuario> listarSeguindo(String emailUser) {
        List<Usuario> usuarios = new ArrayList<>();

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Nodes.USUARIO, "Email", emailUser);

            for (Relationship relacionamento : node.getRelationships(Direction.OUTGOING, Relacionamentos.SEGUIR)) {
                Node node2 = relacionamento.getEndNode();

                Usuario user = new Usuario();
                user.setEmail((String) node2.getProperty("Email"));
                user.setNome((String) node2.getProperty("Nome"));
                user.setSenha((String) node2.getProperty("Senha"));

                usuarios.add(user);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return usuarios;
    }

    public boolean seguir(String emailUser, String emailDestiny) {

        try(Transaction tx = conexao.beginTx()) {
            Node node1 = conexao.findNode(Nodes.USUARIO, "Email", emailUser);
            Node node2 = conexao.findNode(Nodes.USUARIO, "Email", emailDestiny);

            node1.createRelationshipTo(node2, Relacionamentos.SEGUIR);

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean remover(Usuario usuario) {

        try(Transaction tx = conexao.beginTx()) {

            //Encontra o usuario
            Node node = conexao.findNode(Nodes.USUARIO, "Email", usuario.getNome());

            //Remove as Postagens
            for (Relationship relationship : node.getRelationships(Direction.OUTGOING, Relacionamentos.POSTAR)) {
                Node nodePost = relationship.getEndNode();
                nodePost.delete();
            }

            //Remove o Usuario
            node.delete();

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Nodes.USUARIO, "Email", usuario.getNome());

            node.setProperty("nome", usuario.getNome());
            node.setProperty("senha", usuario.getSenha());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }
}
