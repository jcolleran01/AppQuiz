package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mpreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_lionking, true),
            new Question(R.string.question_starwars, false),
            new Question(R.string.question_indianajones, true),
            new Question(R.string.question_startrek, false),
            new Question(R.string.question_jaws, true),
            new Question(R.string.question_harrypotter, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(this);
        //updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(this);

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(this);

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);

        mpreviousButton = (ImageButton) findViewById(R.id.previousbutton);
        mpreviousButton.setOnClickListener(this);

    }
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        String messageResId = "";

        if(userPressedTrue == answerIsTrue){
            messageResId = "Correct!";
        }
        else{
            messageResId = "Incorrect!";
        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void show(String toastDisplay){
        Toast toast = Toast.makeText(MainActivity.this, toastDisplay, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.true_button){
            checkAnswer(true);
        }
        else if (v.getId() == R.id.false_button){
            checkAnswer(false);
        }
        else if (v.getId() == R.id.next_button || v.getId() == R.id.question_text_view){
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        }
        else if (v.getId() == R.id.previousbutton){
           if (mCurrentIndex > 0){
               mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
               updateQuestion();
           }
        }
    }

    public static class Question {
        private int mTextResId;
        private boolean mAnswerTrue;

        public Question(int textResId, boolean answerTrue){
            mTextResId = textResId;
            mAnswerTrue = answerTrue;
        }

        public int getTextResId() {
            return mTextResId;
        }

        public void setTextResId(int textResId) {
            mTextResId = textResId;
        }

        public boolean isAnswerTrue() {
            return mAnswerTrue;
        }

        public void setAnswerTrue(boolean answerTrue) {
            mAnswerTrue = answerTrue;
        }
    }
}
