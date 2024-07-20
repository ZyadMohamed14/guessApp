package com.example.g4dguessapp;

import android.widget.ImageView;
import android.widget.TextView;

public class GameItem {
    private int number;

    private boolean isCardShown;
    private boolean isGameStared;

    public GameItem(int number) {
        this.number = number;

    }

    public boolean isGameStared() {
        return isGameStared;
    }

    public void setGameStared(boolean gameStared) {
        isGameStared = gameStared;
    }

    public GameItem(int number, boolean isCardShown, boolean isGameStared) {
        this.number = number;
        this.isCardShown = isCardShown;
        this.isGameStared = isGameStared;
    }

    public GameItem(int number, boolean isCardShown) {
        this.number = number;
        this.isCardShown = isCardShown;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public boolean isCardShown() {
        return isCardShown;
    }

    public void setCardShown(boolean cardShown) {
        isCardShown = cardShown;
    }
}