package mage.choices;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ChoiceColor extends ChoiceImpl {

    private static final List<String> colorChoices = getBaseColors();

    public static List<String> getBaseColors() {
        List<String> arr = new ArrayList<>();
        arr.add("White");
        arr.add("Blue");
        arr.add("Black");
        arr.add("Red");
        arr.add("Green");
        return arr;
    }

    public ChoiceColor() {
        this(true);
    }

    public ChoiceColor(boolean required) {
        this(required, "Choose color");
    }

    public ChoiceColor(boolean required, String chooseMessage) {
        this(required, chooseMessage, "");
    }

    public ChoiceColor(boolean required, String chooseMessage, MageObject source) {
        this(required, chooseMessage, source.getIdName());
    }

    public ChoiceColor(boolean required, String chooseMessage, String chooseSubMessage) {
        super(required);

        this.choices.addAll(colorChoices);

        this.setMessage(chooseMessage);
        this.setSubMessage(chooseSubMessage);
    }

    protected ChoiceColor(final ChoiceColor choice) {
        super(choice);
    }

    @Override
    public ChoiceColor copy() {
        return new ChoiceColor(this);
    }

    public void removeColorFromChoices(String colorName) {
        this.choices.remove(colorName);
    }

    public ObjectColor getColor() {
        return getColorFromString(choice);
    }

    public static ObjectColor getColorFromString(String colorString) {
        if (colorString == null) {
            return null;
        }
        ObjectColor color = new ObjectColor();
        switch (colorString) {
            case "Black":
                color.setBlack(true);
                break;
            case "Blue":
                color.setBlue(true);
                break;
            case "Green":
                color.setGreen(true);
                break;
            case "Red":
                color.setRed(true);
                break;
            case "White":
                color.setWhite(true);
                break;
        }
        return color;
    }

    public Mana getMana(int amount) {
        Mana mana;
        if (getColor().isBlack()) {
            mana = Mana.BlackMana(amount);
        } else if (getColor().isBlue()) {
            mana = Mana.BlueMana(amount);
        } else if (getColor().isRed()) {
            mana = Mana.RedMana(amount);
        } else if (getColor().isGreen()) {
            mana = Mana.GreenMana(amount);
        } else if (getColor().isWhite()) {
            mana = Mana.WhiteMana(amount);
        } else {
            mana = Mana.ColorlessMana(amount);
        }
        return mana;
    }

    public void increaseMana(Mana mana) {
        if (getColor().isBlack()) {
            mana.increaseBlack();
        } else if (getColor().isBlue()) {
            mana.increaseBlue();
        } else if (getColor().isRed()) {
            mana.increaseRed();
        } else if (getColor().isGreen()) {
            mana.increaseGreen();
        } else if (getColor().isWhite()) {
            mana.increaseWhite();
        } else {
            mana.increaseColorless();
        }
    }
}
