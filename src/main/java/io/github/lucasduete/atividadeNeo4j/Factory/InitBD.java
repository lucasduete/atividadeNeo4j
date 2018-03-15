package io.github.lucasduete.atividadeNeo4j.Factory;

import io.github.lucasduete.atividadeNeo4j.Dao.Neo4j.PostagemDaoNeo4j;
import io.github.lucasduete.atividadeNeo4j.Dao.Neo4j.UsuarioDaoNeo4j;
import io.github.lucasduete.atividadeNeo4j.Model.Postagem;
import io.github.lucasduete.atividadeNeo4j.Model.Usuario;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

public class InitBD {

    public InitBD() {
        initalization();
        insertions();
    }

    public void initalization() {
        GraphDatabaseService conn = Conexao.getConexao();

        try(Transaction tx = conn.beginTx()) {

            //Inicializa Propriedades do BD
            conn.schema().indexFor(Label.label("Usuario")).on("email").create();
            conn.schema().constraintFor(Label.label("Usuario")).assertPropertyIsUnique("email").create();

            tx.success();
        } finally {
            conn.shutdown();
        }
    }

    public void insertions() {
        GraphDatabaseService conn = Conexao.getConexao();

        try(Transaction tx = conn.beginTx()) {
            //Cria Alguns Usuarios
            Usuario user1 = new Usuario("lucas@gmail.com", "lucas", "lucas");
            Usuario user2 = new Usuario("maria@gmail.com", "maria", "maria");
            Usuario user3 = new Usuario("jose@gmail.com", "jose", "jose");
            Usuario user4 = new Usuario("ana@gmail.com", "ana", "ana");

            //Cria Algumas Postagens
            Postagem post1_1 = new Postagem(0, "Esse BD é maneirao");
            Postagem post1_2 = new Postagem(0, "Drive Embarcado(Communit) >>>>> Client");
            Postagem post2_1 = new Postagem(0, "Tem 'Hibernate' pro Neo4j?");
            Postagem post3_1 = new Postagem(0, "Nunca nem vi. Que dia foi isso?");
            Postagem post4_1 = new Postagem(0, "Bom Dia Familia!!! :) <3");

            //Persiste os Usuarios
            new UsuarioDaoNeo4j().cadastrar(user1);
            new UsuarioDaoNeo4j().cadastrar(user2);
            new UsuarioDaoNeo4j().cadastrar(user3);
            new UsuarioDaoNeo4j().cadastrar(user4);

            //Persiste as Postagens
            new PostagemDaoNeo4j().cadastrar(post1_1, user1.getEmail());
            new PostagemDaoNeo4j().cadastrar(post1_2, user1.getEmail());
            new PostagemDaoNeo4j().cadastrar(post2_1, user2.getEmail());
            new PostagemDaoNeo4j().cadastrar(post3_1, user3.getEmail());
            new PostagemDaoNeo4j().cadastrar(post4_1, user4.getEmail());

            //Persiste Alguns Relacionamentos de Seguir Aleatorios Entre Usuarios
            new UsuarioDaoNeo4j().seguir(user1.getEmail(), user2.getEmail());
            new UsuarioDaoNeo4j().seguir(user1.getEmail(), user3.getEmail());
            new UsuarioDaoNeo4j().seguir(user1.getEmail(), user4.getEmail());

            new UsuarioDaoNeo4j().seguir(user2.getEmail(), user3.getEmail());
            new UsuarioDaoNeo4j().seguir(user2.getEmail(), user1.getEmail());

            new UsuarioDaoNeo4j().seguir(user3.getEmail(), user4.getEmail());

            new UsuarioDaoNeo4j().seguir(user4.getEmail(), user3.getEmail());
            new UsuarioDaoNeo4j().seguir(user4.getEmail(), user1.getEmail());

            tx.success();
        } finally {
            conn.shutdown();
        }
    }
}
