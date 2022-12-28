package com.example.projetdma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class TaskAdpter extends RecyclerView.Adapter<TaskAdpter.MyViewHolder>{

    Context context;
    ArrayList<Task_item> task_item;



    public TaskAdpter (Context c, ArrayList<Task_item> p) {
        context = c;
        task_item = p;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task,viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.titledoes.setText(task_item.get(i).getTitledoes());
        myViewHolder.descdoes.setText(task_item.get(i).getDescdoes());
        myViewHolder.datedoes.setText(task_item.get(i).getDatedoes());
        //myViewHolder.keydoes.setText(task_item.get(i).getKeydoes());   mandirohch psq mara7ch 7ataficha

        final String getTitleDoes = task_item.get(i).getTitledoes();
        final String getDescDoes = task_item.get(i).getDescdoes();
        final String getDateDoes = task_item.get(i).getDatedoes();
        final String getKeydoes = task_item.get(i).getKeydoes();


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context,EditTask.class);
                //putExtra t5lina ndiro bch ndiro update lal value  li kayna f database(titletask,desctask,datetask),
                //trecupiriha mad dbb w t7atli les value gettitledoes ta3 la page edit task.
                aa.putExtra("titleTask", getTitleDoes);
                aa.putExtra("descTask", getDescDoes);
                aa.putExtra("dateTask", getDateDoes);
                aa.putExtra("keydoes", getKeydoes); // njiboh brk manafichiwahch, ns7a9oh apres f update

                context.startActivity(aa);

            }
        });



    }


    @Override
    public int getItemCount() {
        return task_item.size();
    }





    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes, descdoes, datedoes, keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = itemView.findViewById(R.id.titledoes);
            descdoes =  itemView.findViewById(R.id.descdoes);
            datedoes =  itemView.findViewById(R.id.datedoes);
            // mandiroch ta3 l key psq ma3andoch id
        }
    }

}