package it.poslaboral.lojaonline.Modelo;

public class PopularModel
{
    String nome, rate, descricao, disconto, tipo, img_url;

    public PopularModel()
    {

    }

    public PopularModel(String nome, String rate, String descricao, String desconto,
                        String tipo, String img_url) {
        this.nome = nome;
        this.rate = rate;
        this.descricao = descricao;
        this.disconto = desconto;
        this.tipo = tipo;
        this.img_url = img_url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDisconto() {
        return disconto;
    }

    public void setDisconto(String desconto) {
        this.disconto = desconto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
