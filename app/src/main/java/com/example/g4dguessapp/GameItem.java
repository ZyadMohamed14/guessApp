package com.example.g4dguessapp;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class GameItem {
    private int number;
    private  int id;
    private boolean isCardShown;
    private boolean isGameStared;

    public int getId() {
        return id;
    }

    public GameItem(int number) {
        this.number = number;
        id= number;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameItem gameItem = (GameItem) o;
        return number == gameItem.number && id == gameItem.id && isCardShown == gameItem.isCardShown && isGameStared == gameItem.isGameStared;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, id, isCardShown, isGameStared);
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