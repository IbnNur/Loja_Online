package www.nazirdaudo.lojaonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminNovoProdutoActivity extends AppCompatActivity
{
    String nomeCategoria, Preco, Descricao, Pnome, guardadataActual, guardahoraActual;
    EditText nome_produto, descricao_produto, preco_produto;
    Button btn_adicionar;
    ImageView id_selecionar_produto;
    static final int selecionar = 1;
    Uri imagemUri;
    String chaveAleatoriaProduto, baixarImagemUrl;
    StorageReference ImagensProdutosRef;
    DatabaseReference ProdutosRef;
    ProgressDialog processo;
    ActivityResultLauncher <Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_novo_produto);

        nomeCategoria = getIntent().getExtras().get("categoria").toString();
        ImagensProdutosRef = FirebaseStorage.getInstance().getReference().child("Imagens de Productos");
        ProdutosRef = FirebaseDatabase.getInstance().getReference().child("Produtos");

        nome_produto = findViewById(R.id.nome_produto);
        descricao_produto = findViewById(R.id.descricao_produto);
        preco_produto = findViewById(R.id.preco_produto);
        btn_adicionar = findViewById(R.id.btn_adicionar);
        id_selecionar_produto = findViewById(R.id.id_selecionar_produto);
        processo = new ProgressDialog(this);



        id_selecionar_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AbrirGaleria.launch("image/*");
            }
        });


        btn_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Descricao = descricao_produto.getText().toString();
                Preco = preco_produto.getText().toString();
                Pnome = nome_produto.getText().toString();

                if (imagemUri == null)
                {
                    Toast.makeText(AdminNovoProdutoActivity.this, "Selecione a imagem", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Descricao))
                {
                    Toast.makeText(AdminNovoProdutoActivity.this, "Digite a descrição do produto", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Preco))
                {
                    Toast.makeText(AdminNovoProdutoActivity.this, "Digite o preço do produto", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Pnome))
                {
                    Toast.makeText(AdminNovoProdutoActivity.this, "Digite o nome do produto", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    InformacaoGuardadaInfo();
                }
            }
        });



    }

    private void InformacaoGuardadaInfo()
    {
        processo.setTitle("Adicionando novo produto");
        processo.setMessage("Por favor aguarde, enquanto é adicionado o novo produto.");
        processo.setCanceledOnTouchOutside(false);
        processo.show();

        Calendar calendario = Calendar.getInstance();

        SimpleDateFormat dataActual = new SimpleDateFormat("MM dd, yyyy");
        guardadataActual = dataActual.format(calendario.getTime());

        SimpleDateFormat horaActual = new SimpleDateFormat(" HH:mm:ss a");
        guardahoraActual = horaActual.format(calendario.getTime());

        chaveAleatoriaProduto = guardadataActual + guardahoraActual;

        StorageReference directorioFicheiro = ImagensProdutosRef.child(imagemUri.getLastPathSegment() + chaveAleatoriaProduto + ".jpg");

        final UploadTask uploadTask = directorioFicheiro.putFile(imagemUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String mensagem = e.toString();
                Toast.makeText(AdminNovoProdutoActivity.this, "Erro: "+mensagem, Toast.LENGTH_SHORT).show();
                processo.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminNovoProdutoActivity.this, "Imagem carregada com sucesso.", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        baixarImagemUrl = directorioFicheiro.getDownloadUrl().toString();
                        return directorioFicheiro.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {

                            baixarImagemUrl = task.getResult().toString();
                            Toast.makeText(AdminNovoProdutoActivity.this, "Imagem guardada com sucesso...", Toast.LENGTH_SHORT).show();

                            GuardarInfoBD();
                        }

                    }
                });
            }
        });

    }

    private void GuardarInfoBD()
    {
        HashMap<String, Object> produtoMap = new HashMap<>();
        produtoMap.put("pid", chaveAleatoriaProduto);
        produtoMap.put("data", guardadataActual);
        produtoMap.put("hora", guardahoraActual);
        produtoMap.put("descricao", Descricao);
        produtoMap.put("imagem", baixarImagemUrl);
        produtoMap.put("categoria", nomeCategoria);
        produtoMap.put("preco", Preco);
        produtoMap.put("pnome", Pnome);

        ProdutosRef.child(chaveAleatoriaProduto).updateChildren(produtoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Intent intent = new Intent(AdminNovoProdutoActivity.this, CategoriaActivity.class);
                    startActivity(intent);

                    processo.dismiss();
                    Toast.makeText(AdminNovoProdutoActivity.this, "Produto Adicionado...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    processo.dismiss();
                    String mensagem = task.getException().toString();
                    Toast.makeText(AdminNovoProdutoActivity.this, "Erro: "+mensagem, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    ActivityResultLauncher<String> AbrirGaleria = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result)
            {
                if (result != null)
                {
                    id_selecionar_produto.setImageURI(result);
                    imagemUri = result;
                }

            }
        });

}
