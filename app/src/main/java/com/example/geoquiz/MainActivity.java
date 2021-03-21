package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "QuizActivity" ;
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String DID_CHEAT = "cheated";


    private Button mCheatButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mpreviousButton;
    private TextView mQuestionTextView;
    private int answersCorrect;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_lionking, true),
            new Question(R.string.question_starwars, false),
            new Question(R.string.question_indianajones, true),
            new Question(R.string.question_startrek, false),
            new Question(R.string.question_jaws, true),
            new Question(R.string.question_harrypotter, true),
    };

    boolean[] questionBooleans = new boolean[]{true, true, true, true, true, true};


    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsCheater = false;
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(DID_CHEAT, false);
        }

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

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                mIsCheater = false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(DID_CHEAT, mIsCheater);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        /*mTrueButton.setEnabled(questionBooleans[mCurrentIndex]);
        mFalseButton.setEnabled(questionBooleans[mCurrentIndex]);*/
    }


    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(mIsCheater) {
            messageResId = R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                answersCorrect++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
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
            questionBooleans[mCurrentIndex] = false;
            mTrueButton.setEnabled(questionBooleans[mCurrentIndex]);
            mFalseButton.setEnabled(questionBooleans[mCurrentIndex]);
            //mTrueButton.setEnabled(false);
            if (mCurrentIndex == mQuestionBank.length - 1){
                show(String.valueOf(100 * (double) answersCorrect/mQuestionBank.length));
            }
        }
        else if (v.getId() == R.id.false_button){
            checkAnswer(false);
            questionBooleans[mCurrentIndex] = false;
            mTrueButton.setEnabled(questionBooleans[mCurrentIndex]);
            mFalseButton.setEnabled(questionBooleans[mCurrentIndex]);
            if (mCurrentIndex == mQuestionBank.length - 1){
                show(String.valueOf(100 * (double) answersCorrect/mQuestionBank.length));
            }
            // mFalseButton.setEnabled(false);
        }
        else if (v.getId() == R.id.next_button || v.getId() == R.id.question_text_view){
            mCurrentIndex = (mCurrentIndex + 1);
            mTrueButton.setEnabled(questionBooleans[mCurrentIndex]);
            mFalseButton.setEnabled(questionBooleans[mCurrentIndex]);
            updateQuestion();
            //i
        }
        else if (v.getId() == R.id.previousbutton){
            if (mCurrentIndex > 0){
                mCurrentIndex = (mCurrentIndex - 1);
                mTrueButton.setEnabled(questionBooleans[mCurrentIndex]);
                mFalseButton.setEnabled(questionBooleans[mCurrentIndex]);
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