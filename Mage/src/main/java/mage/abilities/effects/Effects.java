package mage.abilities.effects;

import mage.abilities.Mode;
import mage.constants.Outcome;
import mage.target.targetpointer.TargetPointer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Effects extends ArrayList<Effect> {

    public Effects() {
    }

    public Effects(Effect effect) {
        this.add(effect);
    }

    public Effects(final Effects effects) {
        for (Effect effect : effects) {
            this.add(effect.copy());
        }
    }

    public Effects copy() {
        return new Effects(this);
    }

    public String getTextStartingUpperCase(Mode mode) {
        String text = getText(mode);
        if (text.length() > 3) {
            return Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }
        return text;
    }

    public String getText(Mode mode) {
        StringBuilder sbText = new StringBuilder();
        String lastRule = null;
        int effectNum = 0;
        for (Effect effect : this) {
            String endString = "";
            String nextRule = effect.getText(mode);

            // ignore empty rules
            if (nextRule == null || nextRule.isEmpty()) {
                continue;
            }
            effectNum++;

            // concat effects (default: each effect with a new sentence)
            String concatPrefix = effect.getConcatPrefix();
            if (effectNum > 1 && !concatPrefix.isEmpty() && !concatPrefix.equals(".")) {
                nextRule = concatPrefix + " " + nextRule;
            }

            if (nextRule != null) {
                if (nextRule.startsWith("and ") || nextRule.startsWith("with ") || nextRule.startsWith("then ")) {
                    endString = " ";
                } else if (nextRule.startsWith(",") || nextRule.startsWith(" ")) {
                    endString = "";
                } else if (lastRule != null && lastRule.length() > 3) {
                    if (!lastRule.endsWith(".") && !lastRule.endsWith("<br>")) {
                        endString = ". ";
                    }
                    if (nextRule.length() > 3) {
                        nextRule = Character.toUpperCase(nextRule.charAt(0)) + nextRule.substring(1);
                    }
                }
                sbText.append(endString).append(nextRule);
            }
            lastRule = nextRule;
        }

        if (lastRule != null && lastRule.length() > 3
                && !lastRule.endsWith(".")
                && !lastRule.endsWith("\"")
                && !lastRule.startsWith("<b>Level ")
                && !lastRule.endsWith(".)")
                && !lastRule.endsWith("</i>")) {
            sbText.append('.');
        }

        return sbText.toString();
    }

    public boolean hasOutcome(Outcome outcome) {
        for (Effect effect : this) {
            if (effect.getOutcome() == outcome) {
                return true;
            }
        }
        return false;
    }

    public List<Outcome> getOutcomes() {
        Set<Outcome> outcomes = new HashSet<>();
        for (Effect effect : this) {
            outcomes.add(effect.getOutcome());
        }
        return new ArrayList<>(outcomes);
    }

    public int getOutcomeTotal() {
        int total = 0;
        for (Effect effect : this) {
            if (effect.getOutcome().isGood()) {
                total++;
            } else {
                total--;
            }
        }
        return total;
    }

    public void newId() {
        for (Effect effect : this) {
            effect.newId();
        }
    }

    public void setTargetPointer(TargetPointer targetPointer) {
        if (targetPointer == null) {
            return;
        }
        for (Effect effect : this) {
            effect.setTargetPointer(targetPointer.copy());
        }
    }

}
