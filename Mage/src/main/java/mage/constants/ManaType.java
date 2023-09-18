package mage.constants;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mage.Mana;
import mage.choices.Choice;
import mage.choices.ChoiceColor;

/**
 *
 * @author North
 */
public enum ManaType {

    BLACK("black"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    WHITE("white"),
    GENERIC("generic"),
    COLORLESS("colorless");

    private final String text;

    ManaType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Choice getChoiceOfManaTypes(Set<ManaType> types, boolean onlyColors) {
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana " + (onlyColors ? "color" : "type"));
        if (types.contains(ManaType.BLACK)) {
            choice.getChoices().add("Black");
        }
        if (types.contains(ManaType.RED)) {
            choice.getChoices().add("Red");
        }
        if (types.contains(ManaType.BLUE)) {
            choice.getChoices().add("Blue");
        }
        if (types.contains(ManaType.GREEN)) {
            choice.getChoices().add("Green");
        }
        if (types.contains(ManaType.WHITE)) {
            choice.getChoices().add("White");
        }
        if (types.contains(ManaType.COLORLESS) && !onlyColors) {
            choice.getChoices().add("Colorless");
        }
        return choice;
    }

    public static List<Mana> getManaListFromManaTypes(Set<ManaType> manaTypes, boolean onlyColors) {
        List<Mana> netManas = new ArrayList<>();
        if ((manaTypes.size() == 5 && !manaTypes.contains(ManaType.COLORLESS)) || manaTypes.size() == 6) { // GENERIC should never be returned from getManaTypes
            netManas.add(Mana.AnyMana(1));
        } else {
            if (manaTypes.contains(ManaType.BLACK)) {
                netManas.add(Mana.BlackMana(1));
            }
            if (manaTypes.contains(ManaType.RED)) {
                netManas.add(Mana.RedMana(1));
            }
            if (manaTypes.contains(ManaType.BLUE)) {
                netManas.add(Mana.BlueMana(1));
            }
            if (manaTypes.contains(ManaType.GREEN)) {
                netManas.add(Mana.GreenMana(1));
            }
            if (manaTypes.contains(ManaType.WHITE)) {
                netManas.add(Mana.WhiteMana(1));
            }
        }
        if (!onlyColors && manaTypes.contains(ManaType.COLORLESS)) {
            netManas.add(Mana.ColorlessMana(1));
        }

        return netManas;
    }

    public static Set<ManaType> getManaTypesFromManaList(Mana mana) {
        Set<ManaType> manaTypes = EnumSet.noneOf(ManaType.class);
        if (mana.getAny() > 0) {
            manaTypes.add(ManaType.BLACK);
            manaTypes.add(ManaType.BLUE);
            manaTypes.add(ManaType.GREEN);
            manaTypes.add(ManaType.WHITE);
            manaTypes.add(ManaType.RED);
        } else {
            if (mana.getBlack() > 0) {
                manaTypes.add(ManaType.BLACK);
            }
            if (mana.getBlue() > 0) {
                manaTypes.add(ManaType.BLUE);
            }
            if (mana.getGreen() > 0) {
                manaTypes.add(ManaType.GREEN);
            }
            if (mana.getWhite() > 0) {
                manaTypes.add(ManaType.WHITE);
            }
            if (mana.getRed() > 0) {
                manaTypes.add(ManaType.RED);
            }
        }
        if (mana.getColorless() > 0) {
            manaTypes.add(ManaType.COLORLESS);
        }
        return manaTypes;
    }

    /**
     * Utility function to find the ManaType associated with a name without needing to have the if-statements
     * cluttering up the code.
     * <p>
     * Used for things like mapping back to a ManaType after the user chose from several of them.
     *
     * @param name  The name of the mana to find
     * @return      The ManaType representing that mana (or null)
     */
    public static ManaType findByName(String name) {
        switch (name) {
            case "Black":
                return ManaType.BLACK;
            case "Blue":
                return ManaType.BLUE;
            case "Red":
                return ManaType.RED;
            case "Green":
                return ManaType.GREEN;
            case "White":
                return ManaType.WHITE;
            case "Colorless":
                return ManaType.COLORLESS;
            default:
                return null;
        }
    }

    public static Set<ManaType> getManaTypesFromManaList(List<Mana> manaList) {
        Set<ManaType> manaTypes = new HashSet<>();
        for (Mana mana : manaList) {
            manaTypes.addAll(getManaTypesFromManaList(mana));
        }
        return manaTypes;
    }
    public static Set<ManaType> getTrueManaTypes() {
        return EnumSet.of(BLACK, BLUE, GREEN, RED, WHITE, COLORLESS);
    }
}
