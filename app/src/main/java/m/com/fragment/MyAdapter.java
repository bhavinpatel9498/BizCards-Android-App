package m.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import m.com.bizcards.MainActivity;
import m.com.bizcards.R;
import m.com.bo.ContactBO;

/**
 * Created by rohit on 4/25/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener{

    private ArrayList<ContactBO> contactList;
    private OnItemClickListener onItemClickListener;
    private final Context contx;

    @Override
    public void onClick(View view){
        Log.d("hello","Hello");

    }





    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView firstname;
        private TextView lastname;
        private TextView company;
        private TextView email;
        private MyViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            firstname = (TextView) v.findViewById(R.id.firstname);
            lastname = (TextView) v.findViewById(R.id.lastname);
            company = (TextView) v.findViewById(R.id.company);
            email = (TextView) v.findViewById(R.id.emailid);

        }

    }

    public MyAdapter(ArrayList<ContactBO> contactList1, Context ctx){
        this.contactList = contactList1;
        this.contx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        v.setOnClickListener(this);
        RecyclerView.ViewHolder holder = new MyViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mClickListener.onClick(view);

                TextView t = view.findViewById(R.id.emailid);
                //Activity act = (Activity)contx;

                CardList c = new CardList();
                c.callContactScreen(t.getText()+"",view.getContext());


            }
        });
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {

        ContactBO contBO = this.contactList.get(position);

        holder.firstname.setText(contBO.getFirstName());
        holder.lastname.setText(contBO.getLastName());
        holder.company.setText(contBO.getCompany());
        holder.email.setText(contBO.getEmail());
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
    @Override
    public int getItemCount() {
            return contactList.size();
    }



}
