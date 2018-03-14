package io.github.lucasduete.atividadeNeo4j.Dao.Neo4j;

import io.github.lucasduete.atividadeNeo4j.Dao.Interfaces.PostagemDaoInterface;
import io.github.lucasduete.atividadeNeo4j.Factory.Conexao;
import io.github.lucasduete.atividadeNeo4j.Model.Postagem;
import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.List;

public class PostagemDaoNeo4j implements PostagemDaoInterface {

    private final GraphDatabaseService conexao;

    public PostagemDaoNeo4j() {
        conexao = Conexao.getConexao();
    }

    @Override
    public boolean cadastrar(Postagem postagem) {

        try(Transaction tx = conexao.beginTx()) {
            Node node = conexao.createNode(Label.label("Postagem"));

            node.setProperty("emailUsuario", postagem.getEmailUsuario());
            node.setProperty("texto",postagem.getTexto());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public List<Postagem> listar() {
        List<Postagem> postagens = new ArrayList<>();

        try(Transaction tx = conexao.beginTx()) {

            ResourceIterator<Node> iterator = conexao.findNodes(Label.label("Postagem"));

            while (iterator.hasNext()) {
                Node node = iterator.next();
                Postagem postagem = new Postagem();

                postagem.setCodigo((int) node.getProperty("__id"));
                postagem.setEmailUsuario((String) node.getProperty("emailUsuario"));
                postagem.setTexto((String) node.getProperty("texto"));

            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

    @Override
    public List<Postagem> listarByUser(String emailUser) {
        List<Postagem> postagens = new ArrayList<>();

        try(Transaction tx = conexao.beginTx()) {

            ResourceIterator<Node> iterator = conexao.findNodes(
                    Label.label("Postagem"), "emailUsuario", emailUser
            );

            while (iterator.hasNext()) {
                Node node = iterator.next();
                Postagem postagem = new Postagem();

                postagem.setCodigo((int) node.getProperty("__id"));
                postagem.setEmailUsuario(emailUser);
                postagem.setTexto((String) node.getProperty("texto"));

            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

}
