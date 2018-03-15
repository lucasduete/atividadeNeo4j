package io.github.lucasduete.atividadeNeo4j.Dao.Neo4j;

import io.github.lucasduete.atividadeNeo4j.Enums.Relacionamentos;
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
    public boolean cadastrar(Postagem postagem, String emailUser) {

        try(Transaction tx = conexao.beginTx()) {
            Node nodePostagem = conexao.createNode(Label.label("Postagem"));

            nodePostagem.setProperty("texto",postagem.getTexto());

            Node nodeUser = conexao.findNode(Label.label("Usuario"), "email", emailUser);
            nodeUser.createRelationshipTo(nodePostagem, Relacionamentos.POSTAR);

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

            Node nodeUsuario = conexao.findNode(Label.label("Usuario"), "email", emailUser);

            for (Relationship relationship : nodeUsuario.getRelationships(Direction.OUTGOING, Relacionamentos.POSTAR)) {
                Node nodePost = relationship.getEndNode();

                Postagem post = new Postagem();
                post.setCodigo(nodePost.getId());
                post.setTexto((String) nodePost.getProperty("texto"));

                postagens.add(post);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

}
