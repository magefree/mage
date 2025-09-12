package mage.constants;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

import java.util.Objects;

public enum ModeChoice {

    KHANS("Khans"),
    DRAGONS("Dragons"),

    MARDU("Mardu"),
    TEMUR("Temur"),
    ABZAN("Abzan"),
    JESKAI("Jeskai"),
    SULTAI("Sultai"),

    MIRRAN("Mirran"),
    PHYREXIAN("Phyrexian"),

    ODD("odd"),
    EVEN("even"),

    BELIEVE("Believe"),
    DOUBT("Doubt"),

    NCR("NCR"),
    LEGION("Legion"),

    BROTHERHOOD("Brotherhood"),
    ENCLAVE("Enclave"),

    ISLAND("Island"),
    SWAMP("Swamp"),

    LEFT("left"),
    RIGHT("right");

    private static class ModeChoiceCondition implements Condition {

        private final ModeChoice modeChoice;

        ModeChoiceCondition(ModeChoice modeChoice) {
            this.modeChoice = modeChoice;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return modeChoice.checkMode(game, source);
        }
    }

    private final String name;
    private final ModeChoiceCondition condition;

    ModeChoice(String name) {
        this.name = name;
        this.condition = new ModeChoiceCondition(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public ModeChoiceCondition getCondition() {
        return condition;
    }

    public boolean checkMode(Game game, Ability source) {
        return Objects.equals(game.getState().getValue(source.getSourceId() + "_modeChoice"), name);
    }
}
