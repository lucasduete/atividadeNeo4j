package io.github.lucasduete.atividadeNeo4j.Model;

import java.util.Objects;

public class Postagem {

    private int codigo;
    private String emailUsuario;
    private String texto;

    public Postagem() {

    }

    public Postagem(int codigo, String emailUsuario, String texto) {

        this.codigo = codigo;
        this.emailUsuario = emailUsuario;
        this.texto = texto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
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
                Objects.equals(getEmailUsuario(), postagem.getEmailUsuario()) &&
                Objects.equals(getTexto(), postagem.getTexto());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCodigo(), getEmailUsuario(), getTexto());
    }

    @Override
    public String toString() {

        return "Postagem{" +
                "codigo=" + codigo +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
