package com.example.helloworld.circle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.circle.entity.Fruit;
import com.example.helloworld.circle.entity.User;
import com.example.helloworld.circle.utility.database.db_fruit;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CircleActivity extends AppCompatActivity {

    private List<Fruit> fruitlist = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle2);
        //模拟数据建立
        initFruits();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        StaggeredGridLayoutManager layoutManager
                = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitlist);
        recyclerView.setAdapter(adapter);

        TextView bt_share = (TextView)findViewById(R.id.bt_share);
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CircleActivity.this,ShareActivity.class);
                startActivity(intent);
            }
    });
        TextView bt_share2 = (TextView)findViewById(R.id.circle_back);
        bt_share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CircleActivity.this, MainActivity_first.class);
//                startActivity(intent);
                finish();
            }
        });

    }
    private void initFruits() {
        User user = new User("10001","测试用户1");
        User user1 = new User("10002","测试用户2");
        Date date = new Date();

        SQLiteDatabase db = LitePal.getDatabase();



        /**
         * 使用LitePal进行数据查询
         */
        List<db_fruit> allfruits = LitePal.order("sendTime DESC").find(db_fruit.class);
        for (db_fruit f : allfruits) {
            //Log.e("db",f.getName());
            Fruit dbitem = new Fruit(user,f.getImageId(),f.getContent(),f.getContent_imgId(),f.getLiked(),f.getComment(),f.getSendTime());
            fruitlist.add(dbitem);
        }
        Log.e("MainTime",date.toString());

        //静态数据
        for (int i = 0; i < 2; i++) {

            Fruit nullitem = new Fruit(user,R.drawable.img_0,"正文测试1正文测试1",
                    R.drawable.img_0,1,"评论",date);
            fruitlist.add(nullitem);
            Fruit apple = new Fruit(user1,R.drawable.img_1,"正文测试2正文测试2正文测试2正文测试2正文测试2正文测试2",
                    R.drawable.img_1,1,"评论",date);
            fruitlist.add(apple);
//            Fruit banana = new Fruit("一生平安", R.drawable.img_2,
//                    "爱情底线：1、如果你心里有别人了，讲清楚，我退出。2、如果你对别人有感觉，讲清楚，我退出。3、如果你不想爱了，我放弃。4、如果你心里还有我，请忘了别人，对我好一点。5、如果你觉得对不起我，而放弃爱别人，这样的同情我不要。"
//                    ,R.drawable.img_0,1,"这是评论嗷");
//            fruitlist.add(banana);
//            Fruit peer = new Fruit("吉祥如意", R.drawable.ic_launcher_foreground, "少一点懒惰，多一点改变，在成为你希望的那种人之前，做好你可以做的事。");
//            fruitlist.add(peer);
        }
    }
    private String getRandomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i<length;i++)
            builder.append(name);
        return  builder.toString();
    }
}
