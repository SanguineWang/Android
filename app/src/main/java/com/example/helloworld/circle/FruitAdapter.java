package com.example.helloworld.circle;


//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;
import com.example.helloworld.circle.entity.Fruit;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;//正文所包含的图片
        TextView fruitName;//用户名字
        CircleImageView fruitImagec;//用户头像
        TextView fruitcontent;//朋友圈正文
        //TextView fruitdiscuss;//朋友圈评论
        ImageView fruitlike;//朋友圈点赞
        TextView fruitTime;//发送时间


        public ViewHolder(View view) {//构造方法
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_images);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitcontent = (TextView) view.findViewById(R.id.fruit_content);
            // fruitdiscuss = (TextView) view.findViewById(R.id.textView);
            fruitImagec = (CircleImageView) view.findViewById(R.id.profile_image);
            fruitlike = (ImageView) view.findViewById(R.id.like);
            fruitTime = (TextView) view.findViewById(R.id.fruit_time);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.fruit_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(),
                        "you clicked view" + fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(),
                        "you clicked image" + fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(),
                        "you clicked Content" + fruit.getContent(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruitlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(positon);

                if (fruit.getLiked() > 0) {
                    Log.d("sas", "哭了");
                    holder.fruitlike.setImageResource(R.drawable.liked);
                    fruit.setLiked(-1);
                } else {
                    Log.d("sas", "笑了");
                    holder.fruitlike.setImageResource(R.drawable.like);
                    fruit.setLiked(1);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Fruit fruit = mFruitList.get(position);
        viewHolder.fruitImage.setImageResource(fruit.getContent_imgId());
        viewHolder.fruitName.setText(fruit.getName());
        viewHolder.fruitImagec.setImageResource(fruit.getImageId());
        viewHolder.fruitcontent.setText(fruit.getContent());
        //viewHolder.fruitdiscuss.setText(fruit.getComment());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.fruitTime.setText(sdf.format(fruit.getSendTime()));
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


}

