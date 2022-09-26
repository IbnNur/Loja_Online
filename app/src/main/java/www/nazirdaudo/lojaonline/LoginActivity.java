package www.nazirdaudo.lojaonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import www.nazirdaudo.lojaonline.Modelo.Usuarios;
import www.nazirdaudo.lojaonline.Prevalidar.Prevalidar;

public class  LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText login_numero_cell, login_senha;
    ProgressDialog processo;
    String parentDbName = "Usuarios";
    com.rey.material.widget.CheckBox lembre_me;
    TextView painel_admin, painel_admin_nao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        login_numero_cell = findViewById(R.id.login_numero_cell);
        login_senha = findViewById(R.id.login_senha);
        painel_admin = findViewById(R.id.painel_admin);
        painel_admin_nao = findViewById(R.id.painel_admin_nao);
        processo = new ProgressDialog(this);
        lembre_me = findViewById(R.id.lembre_me);
        Paper.init(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Entrar();
            }
        });

        painel_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login.setText("Entrar como admin");
                painel_admin.setVisibility(View.INVISIBLE);
                painel_admin_nao.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        painel_admin_nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login.setText("Entrar");
                painel_admin.setVisibility(View.VISIBLE);
                painel_admin_nao.setVisibility(View.INVISIBLE);
                parentDbName = "Usuarios";
            }
        });

    }

    private void Entrar() {
        String numero = login_numero_cell.getText().toString();
        String senha = login_senha.getText().toString();

        if(TextUtils.isEmpty(numero))
        {
            Toast.makeText(this, "Digite seu número", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(senha))
        {
            Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
        }
        else
        {
            processo.setTitle("Entrar na conta");
            processo.setMessage("Por favor aguarde, estamos verificando seus dados.");
            processo.setCanceledOnTouchOutside(false);
            processo.show();

            PermitirAcesso(numero, senha);
        }

    }

    private void PermitirAcesso(String numero, String senha) {

        if (lembre_me.isChecked())
        {
            Paper.book().write(Prevalidar.CelularUsuarioChave, numero);
            Paper.book().write(Prevalidar.SenhaUsuarioChave, senha );
        }
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.child(parentDbName).child(numero).exists())
                {
                    Usuarios dadosUsuario = snapshot.child(parentDbName).child(numero).getValue(Usuarios.class);

                    if (dadosUsuario.getNumero().equals(numero))
                    {
                        if (dadosUsuario.getSenha().equals(senha))
                        {
                            if (parentDbName.equals("Admins"))
                            {

                                Toast.makeText(LoginActivity.this, "Bem vindo Administrador, Entrou com sucesso na sua conta...", Toast.LENGTH_SHORT).show();
                                processo.dismiss();

                                Intent intent = new Intent(LoginActivity.this, CategoriaActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Usuarios"))
                            {
                                Toast.makeText(LoginActivity.this, "Entrou com sucesso na sua conta...", Toast.LENGTH_SHORT).show();
                                processo.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalidar.usuarioOnline = dadosUsuario;
                                startActivity(intent);
                            }

                        }
                        else
                        {
                            processo.dismiss();
                            Toast.makeText(LoginActivity.this, "Senha errada.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Conta com o número: "+numero+" não existe, crie uma conta.", Toast.LENGTH_SHORT).show();
                    processo.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}