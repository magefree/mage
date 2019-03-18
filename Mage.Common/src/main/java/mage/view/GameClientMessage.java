

package mage.view;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import mage.choices.Choice;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameClientMessage implements Serializable {
    @Expose
    private static final long serialVersionUID = 1L;
    @Expose
    private GameView gameView;
    @Expose
    private CardsView cardsView;
    @Expose
    private CardsView cardsView2;
    @Expose
    private String message;
    @Expose
    private boolean flag;
    @Expose
    private String[] strings;
    @Expose
    private Set<UUID> targets;
    @Expose
    private int min;
    @Expose
    private int max;
    @Expose
    private Map<String, Serializable> options;
    @Expose
    private Choice choice;

    public GameClientMessage(GameView gameView) {
        this.gameView = gameView;
    }

    public GameClientMessage(GameView gameView, String message) {
        this.gameView = gameView;
        this.message = message;
    }

    public GameClientMessage(GameView gameView, String message, Map<String, Serializable> options) {
        this.gameView = gameView;
        this.message = message;
        this.options = options;
    }

    private GameClientMessage(GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required) {
        this.gameView = gameView;
        this.message = question;
        this.cardsView = cardView;
        this.targets = targets;
        this.flag = required;
    }

    public GameClientMessage(GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        this(gameView, question, cardView, targets, required);
        this.options = options;
    }

    public GameClientMessage(String[] choices, String message) {
        this.strings = choices;
        this.message = message;
    }

    public GameClientMessage(String message, int min, int max) {
        this.message = message;
        this.min = min;
        this.max = max;
    }

    public GameClientMessage(String message, CardsView pile1, CardsView pile2) {
        this.message = message;
        this.cardsView = pile1;
        this.cardsView2 = pile2;
    }

    public GameClientMessage(CardsView cardView, String name) {
        this.cardsView = cardView;
        this.message = name;
    }

    public GameClientMessage(Choice choice) {
        this.choice = choice;
    }

    public GameView getGameView() {
        return gameView;
    }

    public CardsView getCardsView() {
        return cardsView;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFlag() {
        return flag;
    }

    public String[] getStrings() {
        return strings;
    }

    public Set<UUID> getTargets() {
        return targets;
    }

    public CardsView getPile1() {
        return cardsView;
    }

    public CardsView getPile2() {
        return cardsView2;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Map<String, Serializable> getOptions() {
        return options;
    }

    public Choice getChoice() {
        return choice;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }

}
