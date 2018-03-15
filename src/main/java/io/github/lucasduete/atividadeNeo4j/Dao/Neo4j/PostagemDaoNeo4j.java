package io.github.lucasduete.atividadeNeo4j.Dao.Neo4j;

import io.github.lucasduete.atividadeNeo4j.Enums.Nodes;
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
            Node nodePostagem = conexao.createNode(Nodes.POSTAGEM);

            nodePostagem.setProperty("Texto",postagem.getTexto());

            Node nodeUser = conexao.findNode(Nodes.USUARIO, "Email", emailUser);
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

            ResourceIterator<Node> iterator = conexao.findNodes(Nodes.POSTAGEM);

            while (iterator.hasNext()) {
                Node node = iterator.next();
                Postagem postagem = new Postagem();

                postagem.setCodigo(node.getId());
                postagem.setTexto((String) node.getProperty("Texto"));

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

            Node nodeUsuario = conexao.findNode(Nodes.USUARIO, "Email", emailUser);

            for (Relationship relationship : nodeUsuario.getRelationships(Direction.OUTGOING, Relacionamentos.POSTAR)) {
                Node nodePost = relationship.getEndNode();

                Postagem post = new Postagem();
                post.setCodigo(nodePost.getId());
                post.setTexto((String) nodePost.getProperty("Texto"));

                postagens.add(post);
            }

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return postagens;
    }

    @Override
    public boolean remover(Postagem postagem) {

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Nodes.POSTAGEM,  "id", postagem.getCodigo());
            node.delete();

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }

    @Override
    public boolean atualizar(Postagem postagem) {

        try(Transaction tx = conexao.beginTx()) {

            Node node = conexao.findNode(Nodes.POSTAGEM,  "id", postagem.getCodigo());
            node.setProperty("Texto",postagem.getTexto());

            tx.success();
        } finally {
            conexao.shutdown();
        }

        return true;
    }
}
