package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.Outcome;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Effects extends ArrayList<Effect> {

    public Effects(Effect... effects) {
        this.addAll(Arrays.asList(effects));
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
            return CardUtil.getTextWithFirstCharUpperCase(text);
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
                if (concatPrefix.contains("<br>")) {
                    nextRule = concatPrefix + CardUtil.getTextWithFirstCharUpperCase(nextRule);
                } else {
                    nextRule = concatPrefix + " " + nextRule;
                }
            }

            //check if nextRule is a new sentence or not.
            if (nextRule.startsWith("and ")
                    || nextRule.startsWith("with ")
                    || nextRule.startsWith("then ")
                    || nextRule.startsWith("or ")) {
                endString = " ";
            } else if (nextRule.startsWith(",") || nextRule.startsWith(" ")) {
                endString = "";
                // nextRule determined to be a new sentence, now check ending of lastRule
            } else if (lastRule != null && lastRule.length() > 3) {
                //check if lastRule already has appropriate punctuation, if so, add a space.
                if (lastRule.endsWith(".\"")
                        || lastRule.endsWith(".]")
                        || lastRule.endsWith(".)")
                        || lastRule.endsWith(".)</i>")
                        || lastRule.endsWith(".")) {
                    endString = " ";
                    // if lastRule does not have appropriate punctuation, add the default ". "
                } else if (!lastRule.endsWith(".") && !lastRule.endsWith("<br>")) {
                    endString = ". ";
                }
                if (nextRule.length() > 3) {
                    nextRule = Character.toUpperCase(nextRule.charAt(0)) + nextRule.substring(1);
                }
            }

            String currentRule = endString + nextRule;
            // fix dot in the combined effect like IfDoCost
            if (sbText.length() > 0 && currentRule.length() > 0) {
                boolean prevTextEndsWithDot = sbText.charAt(sbText.length() - 1) == '.';
                boolean currentTextStartsWithDot = currentRule.startsWith(",") || currentRule.startsWith(".");
                if (prevTextEndsWithDot && currentTextStartsWithDot) {
                    sbText.delete(sbText.length() - 1, sbText.length());
                }
            }

            sbText.append(currentRule);

            lastRule = nextRule;
        }

        // add punctuation to very last rule.
        if (lastRule != null && lastRule.length() > 3
                && !lastRule.endsWith(".")
                && !lastRule.endsWith("\"")
                && !lastRule.endsWith(".]")
                && !lastRule.startsWith("<b>LEVEL ")
                && !lastRule.endsWith(".)")
                && !lastRule.endsWith("<br>")
                && !lastRule.endsWith("</i>")) {
            sbText.append('.');
        }

        // flavor word
        if (mode.getFlavorWord() != null) {
            return CardUtil.italicizeWithEmDash(mode.getFlavorWord())
                    + CardUtil.getTextWithFirstCharUpperCase(sbText.toString());
        }

        return sbText.toString();
    }

    public boolean hasOutcome(Ability source, Outcome outcome) {
        Outcome realOutcome = (source == null ? null : source.getCustomOutcome());
        if (realOutcome != null) {
            return realOutcome == outcome;
        }
        for (Effect effect : this) {
            if (effect.getOutcome() == outcome) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param source source ability for effects
     * @return real outcome of ability
     */
    public Outcome getOutcome(Ability source) {
        return getOutcome(source, Outcome.Detriment);
    }

    public Outcome getOutcome(Ability source, Outcome defaultOutcome) {
        Outcome realOutcome = (source == null ? null : source.getCustomOutcome());
        if (realOutcome != null) {
            return realOutcome;
        }

        if (!this.isEmpty()) {
            return this.get(0).getOutcome();
        }

        return defaultOutcome;
    }

    /**
     * @param source source ability for effects
     * @return total score of outcome effects (plus/minus)
     */
    public int getOutcomeScore(Ability source) {
        int total = 0;
        for (Effect effect : this) {
            // custom ability outcome must "rewrite" effect's outcome (it uses for AI desisions and card score... hmm, getOutcomeTotal used on 28.01.2020)
            Outcome realOutcome = (source == null ? null : source.getCustomOutcome());
            if (realOutcome == null) {
                realOutcome = effect.getOutcome();
            }
            if (realOutcome.isGood()) {
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

    public void setValue(String key, Object value) {
        for (Effect effect : this) {
            effect.setValue(key, value);
        }
    }
}
