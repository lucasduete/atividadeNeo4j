package io.github.lucasduete.atividadeNeo4j.Factory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class Conexao {

    private static final File BANCO = new File("twitter_clone.db");
    private static GraphDatabaseService graphDB;

    public static GraphDatabaseService getConexao() {
        graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(BANCO);

        return graphDB;
    }

    public static void closeConexao() {
        graphDB.shutdown();
    }

}
