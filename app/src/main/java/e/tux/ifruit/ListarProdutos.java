package e.tux.ifruit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListarProdutos extends AppCompatActivity {

    private ListView listView;
    private Produto rProduto;
    private String nome;
    private DatabaseReference mBanco;
    private TextView txTeste;
    private TextView txTeste2;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        final String proprietario = user.getEmail().substring(0, user.getEmail().indexOf("@"));

        txTeste = findViewById(R.id.txTeste);
        mBanco = FirebaseDatabase.getInstance().getReference().child("Produto").child(proprietario);

        mBanco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Produto produto = dataSnapshot.getChildren().iterator().next().getValue(Produto.class);
                if (produto == null){
                    txTeste.setText("Retornou banco vazio");
                } else{
                    txTeste.setText(produto.nome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //mBanco.addValueEventListener(listaProdutos);
        //txTeste.setText(rProduto.nome.toString());
        String[] dados = new String[]{String.valueOf(mBanco),"item02"};
        listView = findViewById(R.id.listaItens);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, dados);
        listView.setAdapter(adapter);
    }

}
