package it.poslaboral.lojaonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import www.nazirdaudo.lojaonline.R;

public class CadastroActivity extends AppCompatActivity {

    Button btn_cadastrar;
    EditText cadastro_nome_usuario, cadastro_numero_cell, cadastro_senha;
    ProgressDialog processo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        cadastro_nome_usuario = findViewById(R.id.cadastro_nome_usuario);
        cadastro_numero_cell = findViewById(R.id.cadastro_numero_cell);
        cadastro_senha = findViewById(R.id.cadastro_senha);

        processo = new ProgressDialog(this);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CriarConta();
            }
        });
    }

    private void CriarConta() {
        String nome = cadastro_nome_usuario.getText().toString();
        String numero= cadastro_numero_cell.getText().toString();
        String senha = cadastro_senha.getText().toString();

        if(TextUtils.isEmpty(nome))
        {
            Toast.makeText(this, "Digite seu nome", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(numero))
        {
            Toast.makeText(this, "Digite seu número", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(senha))
        {
            Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
        }
        else
        {
            processo.setTitle("Criar conta");
            processo.setMessage("Por favor aguarde, estamos verificando seus dados.");
            processo.setCanceledOnTouchOutside(false);
            processo.show();

            ValidarNumero(nome, numero, senha);
        }

    }

    private void ValidarNumero(String nome, String numero, String senha)
    {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (!(snapshot.child("Usuarios").child(numero).exists()))
                {
                    HashMap<String, Object> dadosUsuario = new HashMap<>();
                    dadosUsuario.put("numero", numero);
                    dadosUsuario.put("senha", senha);
                    dadosUsuario.put("nome", nome);

                    Rootref.child("Usuarios").child(numero).updateChildren(dadosUsuario)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(CadastroActivity.this, "Parabéns, sua conta foi criada.", Toast.LENGTH_SHORT).show();
                                        processo.dismiss();

                                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        processo.dismiss();
                                        Toast.makeText(CadastroActivity.this, "Problemas com a internet. Tente depois.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(CadastroActivity.this, "O número"+numero+"já foi registado.", Toast.LENGTH_SHORT).show();
                    processo.dismiss();
                    Toast.makeText(CadastroActivity.this, "Tente outro número", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}