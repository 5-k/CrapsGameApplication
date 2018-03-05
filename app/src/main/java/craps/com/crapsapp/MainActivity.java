package craps.com.crapsapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayout;
    private ImageView mainDice1,mainDice2;
    private ImageView yourPointDice1, yourPointDice2;
    private ImageView youRolledDicePoint1,youRolledDicePoint2;
    private TextView yourPointValue,youRolledPointValue, statusText;
    private Button playRestartButton, rollDiceButton;
    private boolean firstRollCompleted;
    private String currentMode;
    String dieImage1 = "@drawable/die1";
    String dieImage2 = "@drawable/die2";
    String dieImage3 = "@drawable/die3";
    String dieImage4 = "@drawable/die4";
    String dieImage5 = "@drawable/die5";
    String dieImage6 = "@drawable/die6";
    int sum;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initVariables();
        setDefault();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        setDefault();
    }

    private void initVariables() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mainDice1 = (ImageView) findViewById(R.id.dice1Main);
        mainDice2 = (ImageView) findViewById(R.id.dice2Main);
        yourPointValue = (TextView) findViewById(R.id.yourPointValue);
        youRolledPointValue = (TextView) findViewById(R.id.YouRolledPointValue);
        yourPointDice1 = (ImageView) findViewById(R.id.yourPointDice1);
        yourPointDice2 = (ImageView) findViewById(R.id.yourPointDice2);
        youRolledDicePoint1 = (ImageView) findViewById(R.id.youRolledDicePoint1);
        youRolledDicePoint2 = (ImageView) findViewById(R.id.youRolledDicePoint2);
        playRestartButton = (Button) findViewById(R.id.playRestartButton);
        rollDiceButton = (Button) findViewById(R.id.RollDiceButton);
        statusText = (TextView) findViewById(R.id.statusText);

        playRestartButton.setOnClickListener(this);
        rollDiceButton.setOnClickListener(this);
    }

    private void setDefault() {
        mainDice1.setImageResource(0);
        mainDice2.setImageResource(0);
        yourPointDice1.setImageResource(0);
        yourPointDice2.setImageResource(0);
        youRolledDicePoint1.setImageResource(0);
        youRolledDicePoint2.setImageResource(0);

        sum = 0;
        rollDiceButton.setEnabled(false);

        yourPointValue.setText("");
        youRolledPointValue.setText("");

        playRestartButton.setText("Play");
        currentMode = "GameNotStarted";
        statusText.setText("Click Play To Start the Game");
        firstRollCompleted = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playRestartButton:
                setDefault();
                if(currentMode.equals("GameNotStarted")) {
                    //Play
                    rollDiceButton.setEnabled(true);
                } else {

                }
                statusText.setText("Roll the dice");
                break;

            case R.id.RollDiceButton:
                playDice();
                break;
        }
    }

    public void playDice() {
        playRestartButton.setText("Restart");
        currentMode = "GameStarted";
        int dice1 = getRandomDiceGenerated();
        int dice2 = getRandomDiceGenerated();
        int sum = dice1 + dice2;

        setImage(mainDice1, dice1);
        setImage(mainDice2, dice2);
        Log.i("Dice1Value", dice1+"");
        Log.i("Dice1Value", dice2+"");
        Log.i("Current Sum", sum+"");
        Log.i("GlobalSum", this.sum+"");

        youRolledPointValue.setText(sum +"");
        if(firstRollCompleted) {
            setImage(youRolledDicePoint1, dice1);
            setImage(youRolledDicePoint2, dice2);

            if(sum == this.sum) {
                statusText.setText(" You Win ! Click To Play again");
                playRestartButton.setText("Play");
                rollDiceButton.setEnabled(false);
                currentMode = "GameNotStarted";
                this.sum = 0;
            } else {
                statusText.setText("You Rolled " + sum + ". Roll Again");
            }
        } else {
            yourPointValue.setText(sum+"");
            setImage(yourPointDice1, dice1);
            setImage(yourPointDice2, dice2);
            handleFirstTimeRoll(dice1, dice2, sum);
            this.sum = sum;

        }
    }

    private void handleFirstTimeRoll(int dice1, int dice2, int sum) {
        this.sum = sum;
        if( sum == 7 || sum == 11) {
            statusText.setText("Player Wins");
            playRestartButton.setText("Play");
            rollDiceButton.setEnabled(false);
            return;
        } else if( sum == 2 || sum == 3 || sum == 12) {
            statusText.setText("Craps ! Player Loses & House Wins");
            playRestartButton.setText("Play");
            rollDiceButton.setEnabled(false);
        } else {
            statusText.setText("Roll Again !");

        }

        firstRollCompleted = true;
    }

    private int getRandomDiceGenerated() {
        Random r = new Random();
        int Low = 1;
        int High = 7;
        return r.nextInt(High-Low) + Low;
    }

    private void setImage(ImageView image, int diceRoll) {
        String url = null;
        switch (diceRoll) {
            case 1:
                url = dieImage1;
                break;
            case 2:
                url = dieImage2;
                break;
            case 3:
                url = dieImage3;
                break;
            case 4:
                url = dieImage4;
                break;
            case 5:
                url = dieImage5;
                break;
            case 6:
                url = dieImage6;
                break;

        }
        int imageId = getResources().getIdentifier(url, null, getPackageName());
        Drawable res = getResources().getDrawable(imageId);
        image.setImageDrawable(res);
    }
}
