package it.poslaboral.lojaonline;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import it.poslaboral.lojaonline.Modelo.Usuarios;
import it.poslaboral.lojaonline.Prevalidar.Prevalidar;
import www.nazirdaudo.lojaonline.R;
 public class MainActivity extends AppCompatActivity {
    Button btn_entrar, btn_registar;
     ProgressDialog processo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_entrar = findViewById(R.id.btn_entrar);
        btn_registar = findViewById(R.id.btn_registar);
        Paper.init(this);
        processo = new ProgressDialog(this);

        btn_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        String CelularUsuarioChave = Paper.book().read(Prevalidar.CelularUsuarioChave);
        String SenhaUsuarioChave = Paper.book().read(Prevalidar.SenhaUsuarioChave);

        if (CelularUsuarioChave != "" && SenhaUsuarioChave != "")
        {
            if (!TextUtils.isEmpty(CelularUsuarioChave) && !TextUtils.isEmpty(SenhaUsuarioChave))
            {
                PermitirAcesso(CelularUsuarioChave, SenhaUsuarioChave);

                processo.setTitle("Sessão já iniciada");
                processo.setMessage("Por favor aguarde...");
                processo.setCanceledOnTouchOutside(false);
                processo.show();
            }
        }
    }

     private void PermitirAcesso(final String numero, final String senha)
     {
         final DatabaseReference Rootref;
         Rootref = FirebaseDatabase.getInstance().getReference();

         Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot)
             {
                 if (snapshot.child("Usuarios").child(numero).exists())
                 {
                     Usuarios dadosUsuario = snapshot.child("Usuarios").child(numero).getValue(Usuarios.class);

                     if (dadosUsuario.getNumero().equals(numero))
                     {
                         if (dadosUsuario.getSenha().equals(senha))
                         {
                             Toast.makeText(MainActivity.this, "Entrou com sucesso na sua conta...", Toast.LENGTH_SHORT).show();
                             processo.dismiss();

                             Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                             startActivity(intent);

                         }
                         else
                         {
                             processo.dismiss();
                             Toast.makeText(MainActivity.this, "Senha errada.", Toast.LENGTH_SHORT).show();
                         }
                     }

                 }
                 else
                 {
                     Toast.makeText(MainActivity.this, "Conta com o número: "+numero+" não existe, crie uma conta.", Toast.LENGTH_SHORT).show();
                     processo.dismiss();
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
     }
 }