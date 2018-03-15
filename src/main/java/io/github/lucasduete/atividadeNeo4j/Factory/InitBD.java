package io.github.lucasduete.atividadeNeo4j.Factory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

public class InitBD {

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
}
