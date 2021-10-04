

package mage.view;

import java.io.Serializable;
import java.util.List;
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
    private CardsView cardsView1;
    @Expose
    private CardsView cardsView2;
    @Expose
    private String message;
    @Expose
    private boolean flag;
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
    @Expose
    private List<String> messages;

    public GameClientMessage(GameView gameView, Map<String, Serializable> options) {
        this.gameView = gameView;
        this.options = options;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, String message) {
        this.gameView = gameView;
        this.options = options;
        this.message = message;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, String message, CardsView cardsView1, Set<UUID> targets, boolean required) {
        this.gameView = gameView;
        this.options = options;
        this.message = message;
        this.cardsView1 = cardsView1;
        this.targets = targets;
        this.flag = required;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, String message, int min, int max) {
        this.gameView = gameView;
        this.options = options;
        this.message = message;
        this.min = min;
        this.max = max;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, String message, CardsView pile1, CardsView pile2) {
        this.gameView = gameView;
        this.options = options;
        this.message = message;
        this.cardsView1 = pile1;
        this.cardsView2 = pile2;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, List<String> messages, int min, int max) {
        this.gameView = gameView;
        this.options = options;
        this.messages = messages;
        this.min = min;
        this.max = max;
    }

    public GameClientMessage(GameView gameView, Map<String, Serializable> options, Choice choice) {
        this.gameView = gameView;
        this.options = options;
        this.choice = choice;
    }

    public GameView getGameView() {
        return gameView;
    }

    public CardsView getCardsView1() {
        return cardsView1;
    }

    public CardsView getCardsView2() {
        return cardsView2;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFlag() {
        return flag;
    }

    public Set<UUID> getTargets() {
        return targets;
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

    public List<String> getMessages() {
        return messages;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }

}
