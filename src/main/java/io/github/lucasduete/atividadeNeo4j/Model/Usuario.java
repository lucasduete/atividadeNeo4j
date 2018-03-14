package io.github.lucasduete.atividadeNeo4j.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private String email;
    private List<Usuario> seguindo;
    private List<Usuario> seguidores;
    private List<Postagem> postagens;

    {
        seguindo = new ArrayList<>();
        seguidores = new ArrayList<>();
        postagens = new ArrayList<>();
    }

    public Usuario() {

    }

    public Usuario(String email, List<Usuario> seguindo,List<Usuario> seguidores, List<Postagem> postagens) {

        this.email = email;
        this.seguindo = seguindo;
        this.seguidores = seguidores;
        this.postagens = postagens;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Usuario> getSeguindo() {
        return seguindo;
    }

    public void setSeguindo(List<Usuario> seguindo) {
        this.seguindo = seguindo;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Postagem> postagens) {
        this.postagens = postagens;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getEmail(), usuario.getEmail()) &&
                Objects.equals(getSeguindo(), usuario.getSeguindo()) &&
                Objects.equals(getSeguidores(), usuario.getSeguidores()) &&
                Objects.equals(getPostagens(), usuario.getPostagens());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail(), getSeguindo(), getSeguidores(), getPostagens());
    }

    @Override
    public String toString() {

        return "Usuario{" +
                "email='" + email + '\'' +
                ", seguindo=" + seguindo +
                ", seguidores=" + seguidores +
                ", postagens=" + postagens +
                '}';
    }
}
