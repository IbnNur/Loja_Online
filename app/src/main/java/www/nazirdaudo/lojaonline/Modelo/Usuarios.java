package www.nazirdaudo.lojaonline.Modelo;

public class Usuarios
{
    String nome, numero, senha;

    public Usuarios()
    {

    }

    public Usuarios(String nome, String numero, String senha) {
        this.nome = nome;
        this.numero = numero;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
