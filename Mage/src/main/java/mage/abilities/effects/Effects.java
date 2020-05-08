package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.Outcome;
import mage.target.targetpointer.TargetPointer;

import java.util.ArrayList;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Effects extends ArrayList<Effect> {

    public Effects(Effect... effects) {
        for (Effect effect : effects) {
            this.add(effect);
        }
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
        System.out.println(" -- getText method called -- ");
        StringBuilder sbText = new StringBuilder();
        String lastRule = null;
        int effectNum = 0;
        for (Effect effect : this) {
            String endString = "";
            String nextRule = effect.getText(mode);

            System.out.printf("Effect Num: %d\n", effectNum);
            System.out.print("nextRule from Effects - getText:: ");
            System.out.println(nextRule);
            System.out.print("lastRule from Effects - getText:: ");
            System.out.println(lastRule);

            // ignore empty rules
            if (nextRule == null || nextRule.isEmpty()) {
                continue;
            }
            effectNum++;

            // concat effects (default: each effect with a new sentence)
            String concatPrefix = effect.getConcatPrefix();
            System.out.print("concatPrefix:");
            System.out.println(concatPrefix);

            if (effectNum > 1 && !concatPrefix.isEmpty() && !concatPrefix.equals(".")) {
                nextRule = concatPrefix + " " + nextRule;
                System.out.println("Catch 1!!");
            }

            if (nextRule != null) {
                if (nextRule.startsWith("and ") || nextRule.startsWith("with ") || nextRule.startsWith("then ")) {
                    endString = " ";
                    System.out.println("Catch 2!!");
                } else if (nextRule.startsWith(",") || nextRule.startsWith(" ")) {
                    endString = "";
                    System.out.println("Catch 3!!");
                } else if (lastRule != null && lastRule.length() > 3) {
                    System.out.println("Catch 4!!");
                    //check if lastRule already has appropriate punctuation, if so, add a space
                    if (lastRule.endsWith(".\"") ||
                            lastRule.endsWith(".)") ||
                            lastRule.endsWith(".)</i>") ||
                            lastRule.endsWith(".")){
                        endString = " ";
                        System.out.println("Catch 9!!");
                     // if lastRule does not have appropriate punctuation, add the default ". "
                    } else if (!lastRule.endsWith(".") && !lastRule.endsWith("<br>")) {
                        endString = ". ";
                        System.out.println("Catch 5!!");
                    }
                    if (nextRule.length() > 3) {
                        System.out.println("Catch 6!!");
                        nextRule = Character.toUpperCase(nextRule.charAt(0)) + nextRule.substring(1);
                    }
                }

                String currentRule = endString + nextRule;
                // fix dot in the combined effect like IfDoCost
                if (sbText.length() > 0 && currentRule.length() > 0) {
                    System.out.println("Catch 7!!");
                    boolean prevTextEndsWithDot = sbText.charAt(sbText.length() - 1) == '.';
                    boolean currentTextStartsWithDot = currentRule.startsWith(",") || currentRule.startsWith(".");
                    if (prevTextEndsWithDot && currentTextStartsWithDot) {
                        System.out.println("Catch 8!!");
                        sbText.delete(sbText.length() - 1, sbText.length());
                    }

                    /*
                    if (!prevTextEndsWithDot){
                        System.out.println("Catch 10!!");
                        currentRule = currentRule + ".";
                    }
                    */
                }



                sbText.append(currentRule);
            }
            lastRule = nextRule;





        }

        //add punctuation to last line
        if (lastRule != null && lastRule.length() > 3
                && !lastRule.endsWith(".")
                && !lastRule.endsWith("\"")
                && !lastRule.startsWith("<b>Level ")
                && !lastRule.endsWith(".)")
                && !lastRule.endsWith("</i>")) {
            sbText.append('.');
        }


        System.out.print("sbText from Effects - getText ::");
        System.out.println(sbText);

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
        this.stream().forEach(effect -> effect.setValue(key, value));
    }
}
