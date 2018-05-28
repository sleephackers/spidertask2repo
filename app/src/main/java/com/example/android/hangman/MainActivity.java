package com.example.android.hangman;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView hangman;
    ArrayList<String> words;
    String currWord;
    TextView dash;
    TextView score;
    String dashes;
    EditText letter;
    String l;
    String fin;
    Button check;
    int wrong=0;
    TextView finish;
    StringBuilder temp;
    Button restart;
    TextView highscore;
    int hscore;
    String wrongGuesses = "INCORRECT: ";
    TextView wGuesses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hangman = findViewById(R.id.hangman);
        dash=(TextView) findViewById(R.id.dash);
        letter=findViewById(R.id.letter);
        check=findViewById(R.id.checkb);
        finish=findViewById(R.id.endtext);
        score= findViewById(R.id.score);
        restart= findViewById(R.id.replay);
        highscore=findViewById(R.id.highscore);
        wGuesses = findViewById(R.id.wguesses);
        dashes="";
        loadInfo();
        loadData();
        changePic(wrong);
        highscore.setText("HIGHSCORE:"+hscore);
        currentWord();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            wrong=0;dashes="";
            score.setText("");
            finish.setText("");
                wGuesses.setText("");
            currentWord();
            changePic(wrong);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (letter.getText().toString().trim().length() <= 0) {
                    Toast.makeText(MainActivity.this, "Enter a letter", Toast.LENGTH_SHORT).show();
                } else {
                    l = letter.getText().toString();
                    checkword();
                }
            }
        });
    }
    public void checkword()
    {
        int flag = 0, flag2 = 0;
        String  all;
        dashes="";
        dash.setText(currWord);
        temp = new StringBuilder(fin);
        for (int j = 0; j < currWord.length(); j++)
        {all=String.valueOf(currWord.charAt(j));
        boolean correct = l.equals(all);

            if(correct) {
                dashes +=all + " ";
                flag++;
            } else {
                dashes += "_ ";
            }
        }

        for(int i=0;i<dashes.length();i++)
        {
            if(dashes.charAt(i)!='_' &&dashes.charAt(i)!=' ')
            {
                temp.setCharAt(i, dashes.charAt(i));
            }
        }
        fin=temp.toString();
            dash.setText(fin);
        if (flag == 0) {
            for (int i = 0; i < wrongGuesses.length(); i++)
                if (wrongGuesses.charAt(i) == l.charAt(0)) flag2++;
            if (flag2 == 0) {
                changePic(++wrong);
                wrongGuesses += " " + l + " ,";
                wGuesses.setText(wrongGuesses);
            }
        }
        else
            over();

    }

    public void over()
    { int let=0;
        for(int i=0;i<fin.length();i++)
        {
            if(fin.charAt(i)!='_' && fin.charAt(i)!=' ')
            {
                let++;
            }
        }
        if(let== currWord.length()) gameOver();

    }
    public void changePic(int n)
    {
        if(n==0)
            hangman.setImageResource(R.drawable.h1);
        if(n==1)
            hangman.setImageResource(R.drawable.h2);
        if(n==2)
            hangman.setImageResource(R.drawable.h3);
        if(n==3)
            hangman.setImageResource(R.drawable.h4);
        if(n==4)
            hangman.setImageResource(R.drawable.h5);
        if(n==5)
            hangman.setImageResource(R.drawable.h6);
        if(n==6) {
            hangman.setImageResource(R.drawable.h7);
            gameOver();
        }

    }

    public void currentWord()
    {   int number;
        Random r = new Random();
        number = (r.nextInt(16) + 0);
         currWord=words.get(number);
         display();
    }

    public void display()
    {
        for (int j = 0; j < currWord.length(); j++)
        {
            dashes+="_ ";
        }
        fin=dashes;
        dash.setText(dashes);
    }
    public void gameOver()
    {
        if(wrong==6)
        finish.setText("The word is:"+currWord);
        else {
            finish.setText("Congrats! You've found out!");
            score.setText("YOUR SCORE IS:" + wrong);
            saveInfo();
            highscore.setText("HIGHSCORE:"+hscore);
        }
    }
    public void wordadd() {
        words.add("tree");
        words.add("monsoon");
        words.add("college");
        words.add("subject");
        words.add("cycle");
        words.add("festember");
        words.add("laptop");
        words.add("fight");
        words.add("classroom");
        words.add("teacher");
        words.add("ragging");
        words.add("cellphone");
        words.add("android");
        words.add("spider");
        words.add("clubs");
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(words);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        words = gson.fromJson(json, type);
        if (words == null) {
            words = new ArrayList<>();
            wordadd();
        }

    }
    private void saveInfo() {
        SharedPreferences sharedpref = getSharedPreferences("scoreInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        if(hscore<wrong);
        else
        {editor.putInt("highscore",wrong);
        hscore=wrong;}
        editor.apply();
    }
    private void loadInfo() {
        SharedPreferences sharedpref = getSharedPreferences("scoreInfo", MODE_PRIVATE);
        hscore=sharedpref.getInt("highscore",6);


    }
}
