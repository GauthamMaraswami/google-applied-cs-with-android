package com.example.gautham.scarnes_dice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TEST_TAG";
    Timer mytimer;
    public int userOverallScore=0;
    public int userTurnScore=0;
    public int computerOverallScore=0;
    public int computerTurnScore=0;
    Button rollButton,holdButton;
    ImageView imageView;
    TextView label;
    TextView win;
    String userScoreLabel = "<b><i>Your score : </i></b>";
    String compScoreLabel = "<b><i> Computer score : </i></b>";
    String userTurnScoreLabel = "<b><i> Your turn score : </i></b>";
    String compTurnScoreLabel = "\n<b><i>computer turn score : </i></b>";

    String labelText = userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore;
    int[] drawables={R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = (Button) findViewById(R.id.roll);
        holdButton = (Button) findViewById(R.id.hold);
        label = (TextView) findViewById(R.id.label);
        imageView = (ImageView) findViewById(R.id.imageView);

        win = (TextView) findViewById(R.id.win);
        label.setText(Html.fromHtml(labelText));

    }
    public void RollButtonClick(View view){

        Log.d(TAG, "rollButtonClick called ");
        int RolledNo=rollDice();
        imageView.setImageResource(drawables[RolledNo]);
        RolledNo++;
        if(RolledNo==1){
            userTurnScore=0;
            labelText=userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore+userTurnScoreLabel +userTurnScore;
            label.setText(Html.fromHtml(labelText));
            computerTurn();
        }
        else {
            userTurnScore+=RolledNo;
            labelText=userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore+userTurnScoreLabel +userTurnScore;
            label.setText(Html.fromHtml(labelText));

        }

    }
    public void holdButtonClick(View view){
        userOverallScore+=userTurnScore;
        userTurnScore=0;
        labelText=userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore;
        label.setText(Html.fromHtml(labelText));
        if(userOverallScore>=100){
            showwin(0);
        }
        else{
        computerTurn();
            if(computerOverallScore>=100)
            { showwin(1);
                rollButton.setEnabled(false);
                holdButton.setEnabled(false);

            }
        }



    }
    public void computerTurn(){
        mytimer=new Timer();
        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableButtons(false);
                    }
                });
                int computerRolledNumber=rollDice();
                final int finalComputerNumber=computerRolledNumber;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(drawables[finalComputerNumber]);
                    }
                });
                computerRolledNumber++;
                if(computerRolledNumber==1){
                    computerTurnScore = 0;
                    labelText = userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore +
                            "\n computer rolled a one and lost it's chance";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(computerOverallScore<100) {
                                enableButtons(true);
                            }
                            else
                            {
                                showwin(1);
                            }
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            label.setText(Html.fromHtml(labelText));
                        }
                    });
                    mytimer.cancel();
                }
                else {

                    computerTurnScore+=computerRolledNumber;
                    labelText = userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore
                            + "\nComputer rolled a " + computerRolledNumber
                            + compTurnScoreLabel + computerTurnScore;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            label.setText(Html.fromHtml(labelText));
                        }
                    });
                    if(computerTurnScore>20){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(computerOverallScore<100){


                                enableButtons(true);
                            }
                            else
                            showwin(1);}
                        });
                        computerOverallScore+=computerTurnScore;
                        computerTurnScore=0;
                        labelText=userScoreLabel+userOverallScore+compScoreLabel+computerOverallScore+"\nComputer holds";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                label.setText(Html.fromHtml(labelText));
                            }
                        });
                        mytimer.cancel();
                    }
                }




            }
        },0,2000);
        if(computerOverallScore>=100){
            showwin(1);
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
        }
    }
    public void resetButtonClick(View view){

        win.setVisibility(View.INVISIBLE);
        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;

        labelText = userScoreLabel + userOverallScore + compScoreLabel + computerOverallScore;
        label.setText(Html.fromHtml(labelText));

        enableButtons(true);
    }
    private int rollDice(){
    Random random = new Random();
        int randomNumber = random.nextInt(6);
        Log.d(TAG,"rollBtnClick: " + randomNumber);
        return randomNumber;
    }
    private void showwin(int who){
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        if(who==1)
        {
            win.setText("Computer won");
            win.setVisibility(View.VISIBLE);

        }
        else{
            win.setText("You Won");
            win.setVisibility(View.VISIBLE);
        }


    }
    private void enableButtons(boolean isEnabled) {
        rollButton.setEnabled(isEnabled);
        holdButton.setEnabled(isEnabled);
    }

}
