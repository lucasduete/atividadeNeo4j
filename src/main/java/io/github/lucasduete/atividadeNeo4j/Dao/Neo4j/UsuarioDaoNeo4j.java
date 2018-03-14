package io.github.lucasduete.atividadeNeo4j.Dao.Neo4j;

import io.github.lucasduete.atividadeNeo4j.Dao.Interfaces.UsuarioDaoInterface;
import io.github.lucasduete.atividadeNeo4j.Factory.Conexao;
import io.github.lucasduete.atividadeNeo4j.Model.Usuario;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.List;

public class UsuarioDaoNeo4j implements UsuarioDaoInterface {

    private final GraphDatabaseService conexao;

    public UsuarioDaoNeo4j() {
        conexao = Conexao.getConexao();
    }

    @Override
    public boolean cadastrar(Usuario usuario) {
        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.createNode(Label.label("Usuario"));

            node.setProperty("email", usuario.getEmail());
            node.setProperty("nome", usuario.getNome());
            node.setProperty("senha", usuario.getSenha());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public List<Usuario> listar() {
        return null;
    }

    @Override
    public List<Usuario> listarSeguidores() {
        return null;
    }

    @Override
    public List<Usuario> listarSeguindo() {
        return null;
    }
}
