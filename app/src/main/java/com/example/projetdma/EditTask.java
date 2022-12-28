package com.example.projetdma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class EditTask extends AppCompatActivity {

    EditText titledoes, descdoes, datedoes;
    Button btnUpdate, btnDelete;

    DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        getSupportActionBar().hide();

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //get a value from previous screen li rahom f taskAdpter li 5rajnahom man bdd
        // w n7atohom direct f ces places ( en violet)

        titledoes.setText(getIntent().getStringExtra("titleTask"));
        descdoes.setText(getIntent().getStringExtra("descTask"));
        datedoes.setText(getIntent().getStringExtra("dateTask"));

        // pour la valeur key njiboha mais manafichiwhach
        // donc n7atoha f variable nsmoha keykeyDoes (ns7a9oha bch ki nkliko 3la save ya3raf win ydir update)

        String keykeyDoes = getIntent().getStringExtra("keydoes");

        // nido refrece m3a bdd , psq ns7a9oha f les 2 button
        reference = FirebaseDatabase.getInstance().getReference().child("task").
                child("Task" + keykeyDoes);


        //make an event for button li dir delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTask.this, MainActivity.class);
                            startActivity(a);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        //make an event for button li dir Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // okay n3awdo nsaviw mais avec update

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // li rahom en violet homa li jbnahom man previous page li modifyin,
                        //n3awdo save them
                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                        // put and resave the key in dbb ( 3chwa2i michi 7na ndiroh)
                        dataSnapshot.getRef().child("keydoes").setValue(keykeyDoes);

                        // si sayi movi man EditTask l PlanActivity
                        Intent a = new Intent(EditTask.this, MainActivity.class);
                        startActivity(a);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });


            }


        });


    }

}