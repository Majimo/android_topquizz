package com.dropbird.android.topquizz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbird.android.topquizz.model.Question;
import com.dropbird.android.topquizz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;
    private int mNumberOfQuestions;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mScore;
    public static final String GLOBAL_SCORE = "GLOBAL_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mScore = 0;

        mNumberOfQuestions = 3;

        mQuestionBank = this.generateQuestions();

        mQuestion = findViewById(R.id.activity_game_question_text);
        mAnswer1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswer2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswer3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswer4 = findViewById(R.id.activity_game_answer4_btn);

        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    private void displayQuestion(final Question question) {
        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question("Qui est le créateur d'Android?", Arrays.asList(
                "Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question2 = new Question("En quelle année Armstrong a-t-il été sur la lune ?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question3 = new Question("Quel est le numéro de la maison des Simpsons ?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        Question question4 = new Question("En quelle année est né Pierre Fervel ?",
                Arrays.asList("1933", "2015", "1984", "1958"),
                2);

        Question question5 = new Question("Qui a peint la Joconde ?",
                Arrays.asList("Michelange", "Leonard De Vinci", "Raphael", "Carravagio"),
                1);

        return new QuestionBank(Arrays.asList(question1, question2, question3,  question4, question5));
    }

    @Override
    public void onClick(View view) {
        int responseId = (int) view.getTag();

        if (responseId == mCurrentQuestion.getAnswerIndex()) {
            mScore = mScore + 100;
            Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Non ! Mauvais !", Toast.LENGTH_SHORT).show();
        }

        if (--mNumberOfQuestions == 0){
            stopGame();
        }
        else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }

    private void stopGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joué !")
                .setMessage("Votre score est de " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(GLOBAL_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }
}
