package io.github.lucasduete.atividadeNeo4j.Model;

import java.util.Objects;

public class Postagem {

    private long codigo;
    private String texto;

    public Postagem() {

    }

    public Postagem(long codigo, String texto) {

        this.codigo = codigo;
        this.texto = texto;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postagem postagem = (Postagem) o;
        return getCodigo() == postagem.getCodigo() &&
                Objects.equals(getTexto(), postagem.getTexto());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCodigo(), getTexto());
    }

    @Override
    public String toString() {

        return "Postagem{" +
                "codigo=" + codigo +
                ", texto='" + texto + '\'' +
                '}';
    }
}
