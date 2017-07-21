package com.kappa.tictactoe;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    boolean isGameActive = true;

    int playCounter = 0;
    int currentPlayer = 0;

    int[] gamePositions = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[][] winningPatterns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void playerTurn(View view)
    {
        ImageView currentTapped = (ImageView) view;

        int tapped = Integer.parseInt(currentTapped.getTag().toString());

        if(isGameActive && gamePositions[tapped] == -1 && currentPlayer == 0)
        {
            gamePositions[tapped] = currentPlayer;

            currentTapped.setImageResource(R.drawable.o);

            checkForWinner();
        }
    }

    public void botTurn()
    {
        new CountDownTimer(1000, 1000)
        {
            public void onFinish()
            {
                int tapped = 0;
                boolean found = false;

                while(!found)
                {
                    int random = (int) Math.floor(Math.random() * 9);

                    if(gamePositions[random] == -1)
                    {
                        tapped = random;
                        found = true;
                    }
                }

                int id = getResources().getIdentifier("imageView" + (tapped + 1), "id", getPackageName());

                ImageView currentTapped = (ImageView) findViewById(id);

                gamePositions[tapped] = currentPlayer;

                currentTapped.setImageResource(R.drawable.x);

                checkForWinner();
            }

            public void onTick(long millisUntilFinished)
            {
            }
        }.start();
    }

    public void checkForWinner()
    {
        TextView playerTurn = (TextView) findViewById(R.id.playerTurn);

        for(int[] i : winningPatterns)
        {
            if((gamePositions[i[0]] == gamePositions[i[1]] && gamePositions[i[1]] == gamePositions[i[2]]) && gamePositions[i[0]] != -1)
            {
                TextView winnerText = (TextView) findViewById(R.id.winnerText);
                ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);

                playerTurn.setText("Game Ended");

                isGameActive = false;

                if(gamePositions[i[0]] == 0)
                {
                    winnerText.setText("You Win!");
                }
                else
                {
                    winnerText.setText("Bot Wins!");
                }

                winLayout.setVisibility(View.VISIBLE);
                winLayout.setAlpha(0f);
                winLayout.animate().setDuration(800).alpha(1f);
            }
        }

        if(isGameActive)
        {
            if(++playCounter == 9)
            {
                TextView winnerText = (TextView) findViewById(R.id.winnerText);
                ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);

                playerTurn.setText("Game Ended");

                isGameActive = false;

                winnerText.setText("No Player Wins!");

                winLayout.setVisibility(View.VISIBLE);
                winLayout.setAlpha(0f);
                winLayout.animate().setDuration(800).alpha(1f);
            }
            else
            {
                if(currentPlayer == 0)
                {
                    currentPlayer = 1;

                    playerTurn.setText("Bots Turn");

                    botTurn();
                }
                else
                {
                    currentPlayer = 0;

                    playerTurn.setText("Your Turn");
                }
            }
        }
    }

    public void playAgain(View view)
    {
        ConstraintLayout winLayout = (ConstraintLayout) findViewById(R.id.winLayout);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        winLayout.setVisibility(View.INVISIBLE);

        TextView playerTurn = (TextView) findViewById(R.id.playerTurn);

        playerTurn.setText("Your Turn");

        isGameActive = true;
        currentPlayer = 0;
        playCounter = 0;

        for(int i = 0; i < gamePositions.length; i++)
        {
            gamePositions[i] = -1;
        }

        for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
