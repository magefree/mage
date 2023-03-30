package mage.client.components.ext.dlg;

import mage.client.cards.BigCard;
import mage.client.components.ext.MessageDialogType;
import mage.client.game.FeedbackPanel;
import mage.util.CardUtil;
import mage.view.CardsView;

import java.awt.*;
import java.util.*;

/**
 * GUI: parameters for dialogs, uses to store useful data
 *
 * @author mw, noxx
 */
public class DlgParams {

    public Rectangle rect;
    public MessageDialogType type;
    public BigCard bigCard;
    public FeedbackPanel feedbackPanel;
    public UUID gameId;

    private int playerID;

    private CardsView cards;
    private java.util.List<String> stringList;
    //private ArrayList<DeckInfo> deckList;
    private java.util.List<Object> objectList;

    private String title;
    private int opponentID;
    private boolean isOptional = false;
    private boolean isChooseAbility = false;
    private boolean isCancelStopsPlaying = true;

    private boolean isAI = false;

    private Set<String> manaChoices = new LinkedHashSet<>();

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getOpponentID() {
        return opponentID;
    }

    public void setOpponentID(int opponentID) {
        this.opponentID = opponentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getManaChoices() {
        return manaChoices;
    }

    public void setManaChoices(Set<String> manaChoices) {
        CardUtil.checkSetParamForSerializationCompatibility(manaChoices);
        this.manaChoices = manaChoices;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }

    public boolean isChooseAbility() {
        return isChooseAbility;
    }

    public void setChooseAbility(boolean isChooseAbility) {
        this.isChooseAbility = isChooseAbility;
    }

    public java.util.List<String> getStringList() {
        return stringList;
    }

    public void setStringList(ArrayList<String> stringList) {
        this.stringList = stringList;
    }

    /*public ArrayList<DeckInfo> getDeckList() {
        return deckList;
    }

    public void setDeckList(ArrayList<DeckInfo> deckList) {
        this.deckList = deckList;
    }*/

    public java.util.List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(ArrayList<Object> objectList) {
        this.objectList = objectList;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }

    public boolean isCancelStopsPlaying() {
        return isCancelStopsPlaying;
    }

    public void setCancelStopsPlaying(boolean isCancelStopsPlaying) {
        this.isCancelStopsPlaying = isCancelStopsPlaying;
    }

    public CardsView getCards() {
        return cards;
    }

    public void setCards(CardsView cards) {
        this.cards = cards;
    }
}