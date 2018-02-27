package com.example.recyclerviewtest;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.StaggeredGridLayoutManager;

        import java.io.RandomAccessFile;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    public void initFruits() {
        for (int i = 0;i < 2;i++ ) {
            Fruit apple = new Fruit(
                   getRandomLengthName("Apple"),R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit(
                    getRandomLengthName("Banana"),R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit(
                    getRandomLengthName("Apple"),R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < length;i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}
