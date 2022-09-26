package it.poslaboral.lojaonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import www.nazirdaudo.lojaonline.R;

public class CategoriaActivity extends AppCompatActivity
{
    ImageView camisetes, camisetes_desporto, vestidos, casacos,
              oculos, bolsas, chapeus, sapatos, auriculares, pcs,
              relogios, celulares;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        camisetes = findViewById(R.id.camisetes);
        camisetes_desporto = findViewById(R.id.camisetes_desporto);
        vestidos = findViewById(R.id.vestidos);
        casacos = findViewById(R.id.casacos);
        oculos = findViewById(R.id.oculos);
        bolsas = findViewById(R.id.bolsas);
        chapeus = findViewById(R.id.chapeus);
        auriculares = findViewById(R.id.auriculares);
        pcs = findViewById(R.id.pcs);
        relogios = findViewById(R.id.relogios);
        celulares = findViewById(R.id.celulares);
        sapatos = findViewById(R.id.sapatos);

        camisetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Camisetes");
                startActivity(intent);
            }
        });

        camisetes_desporto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Camisetes Desporto");
                startActivity(intent);

            }
        });

        vestidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Vestidos");
                startActivity(intent);
            }
        });

        casacos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Casacos");
                startActivity(intent);
            }
        });

        oculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Óculos");
                startActivity(intent);
            }
        });

        bolsas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Bolsas");
                startActivity(intent);

            }
        });

        chapeus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Chápeus");
                startActivity(intent);
            }
        });

        sapatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Sapatos");
                startActivity(intent);
            }
        });

        auriculares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Auriculares");
                startActivity(intent);
            }
        });

        pcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "PC's");
                startActivity(intent);
            }
        });

        relogios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Relógios");
                startActivity(intent);
            }
        });

        celulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriaActivity.this, AdminNovoProdutoActivity.class);
                intent.putExtra("categoria", "Celulares");
                startActivity(intent);
            }
        });
    }
}