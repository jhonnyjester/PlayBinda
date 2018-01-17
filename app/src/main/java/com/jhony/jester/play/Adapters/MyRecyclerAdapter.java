package com.jhony.jester.play.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Activitys.BindaItemClickListener;
import com.jhony.jester.play.Activitys.GameActivity;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.GRID;
import static com.jhony.jester.play.Utils.Constants.RECYCLER;
import static com.jhony.jester.play.Utils.DataSingleton.endPoint;
import static com.jhony.jester.play.Utils.DataSingleton.gameSize;
import static com.jhony.jester.play.Utils.DataSingleton.isHost;
import static com.jhony.jester.play.Utils.DataSingleton.numbers;

/**
 * Created by JAR on 06-01-2018.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


    Context context;
    String what, payload;
    View view;
    private BindaItemClickListener bindaItemClickListener;

    public MyRecyclerAdapter(Context context, String what) {
        this.context = context;
        this.what = what;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (what) {
            case GRID:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_single_item, parent, false);
                break;
            case RECYCLER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_single_item, parent, false);
                break;
            default:
                return null;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        switch (what) {
            case RECYCLER:
                //use glide to set the player image
                // set description and name
                Glide.with(context).load(R.drawable.vector).into(holder.mUserImage);
                if (position == getItemCount() - 1) {
                    if (isHost)
                        holder.mWaiting.setVisibility(View.VISIBLE);
                } else holder.mWaiting.setVisibility(View.GONE);
                if (position % 2 == 0) {
                    holder.mPlayerStatus.setImageResource(R.drawable.ic_ready);
                } else {
                    holder.mPlayerStatus.setImageResource(R.drawable.ic_not_ready);
                    holder.mPlayerStatus.setRotation(45);
                }

                holder.mContainer.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(context, "long clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                break;

            case GRID:
                holder.rowTv.setText(String.valueOf(DataSingleton.numbers.get(position)));

                holder.mGridContainer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!DataSingleton.didWin && DataSingleton.myTurn)
                            if (!DataSingleton.isTicked.get(position)) {
                                DataSingleton.isTicked.set(position, true);
//                                holder.mGridContainer.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                                holder.mGridContainer.setBackground(context.getResources().getDrawable(R.drawable.gradient_green));
                                holder.rowTv.setTextColor(context.getResources().getColor(R.color.white));
                                payload = String.valueOf(numbers.get(position));
                                bindaItemClickListener.onItemClick(position);
                            }
                    }
                });
                break;
        }
    }

    public void setListener(BindaItemClickListener bindaItemClickListener) {
        this.bindaItemClickListener = bindaItemClickListener;
    }

    @Override
    public int getItemCount() {
        switch (what) {
            case RECYCLER:
                return 10 + 1;
            case GRID:
                return gameSize * gameSize;
            default:
                return 3;
        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mUserImage, mPlayerStatus;
        TextView mUserName, mUserDesc, mExp, mWaiting, rowTv;
        CardView mContainer, mGridContainer;

        public MyViewHolder(View itemView) {
            super(itemView);

            mUserImage = itemView.findViewById(R.id.user_image);
            mPlayerStatus = itemView.findViewById(R.id.ready_or_not);
            mUserName = itemView.findViewById(R.id.user_name_players);
            mUserDesc = itemView.findViewById(R.id.player_desc);
            mExp = itemView.findViewById(R.id.exp_players);
            mWaiting = itemView.findViewById(R.id.waiting_tv);
            rowTv = itemView.findViewById(R.id.row_tv);
            mContainer = itemView.findViewById(R.id.row_container);
            mGridContainer = itemView.findViewById(R.id.row_card_view);
        }
    }

}