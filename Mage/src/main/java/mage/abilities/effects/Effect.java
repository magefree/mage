
package mage.abilities.effects;

import java.io.Serializable;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Effect extends Serializable {

    UUID getId();

    void newId();

    /**
     * Some general behaviours for rule text handling: Rule text of effects get
     * automatically a full stop "." at the end, if not another effect e.g. with
     * a starting "and" follows. So at least for effects of the framework, that
     * are used from multiple cards, it's better to set no full stop at the end
     * of the rule text of an effect. Also the starting letter of an effect is
     * automatically converted to upper case if the rule text starts with this
     * text. So There is no need to let the effect text start with upper case,
     * even if extracted from a filter message. Also here it's important to use
     * only lower cases for effects located in the framework, so if used for a
     * triggered abilitiy, the effect text needs to start with lower case after
     * the comma.
     *
     * @param mode the selected mode of the ability (mostly there is only one)
     * @return
     */
    String getText(Mode mode);

    Effect setText(String staticText);

    boolean apply(Game game, Ability source);

    /**
     * The outcome is used for the AI to decide if an effect does bad or good to
     * the targets.
     *
     * @return
     */
    Outcome getOutcome();

    void setOutcome(Outcome outcome);

    EffectType getEffectType();

    Effect setTargetPointer(TargetPointer targetPointer);

    TargetPointer getTargetPointer();

    void setValue(String key, Object value);

    Object getValue(String key);

    Effect copy();

}
