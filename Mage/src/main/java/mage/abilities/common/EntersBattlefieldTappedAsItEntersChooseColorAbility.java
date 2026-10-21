
package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.constants.Outcome;
import mage.constants.Zone;

import java.util.Locale;

/**
 * @author Susucr
 */
public class EntersBattlefieldTappedAsItEntersChooseColorAbility extends StaticAbility {

    private final String exceptColor;

    public EntersBattlefieldTappedAsItEntersChooseColorAbility() {
        this((String) null);
    }

    public EntersBattlefieldTappedAsItEntersChooseColorAbility(String exceptColor) {
        super(Zone.ALL, new EntersBattlefieldEffect(new TapSourceEffect(true)));
        this.addEffect(new ChooseColorEffect(Outcome.Benefit, exceptColor));
        this.exceptColor = exceptColor;
    }

    private EntersBattlefieldTappedAsItEntersChooseColorAbility(final EntersBattlefieldTappedAsItEntersChooseColorAbility ability) {
        super(ability);
        this.exceptColor = ability.exceptColor;
    }

    @Override
    public EntersBattlefieldTappedAsItEntersChooseColorAbility copy() {
        return new EntersBattlefieldTappedAsItEntersChooseColorAbility(this);
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect entersEffect = this.getEffects().get(0);
            if (entersEffect instanceof EntersBattlefieldEffect) {
                ((EntersBattlefieldEffect) entersEffect).addEffect(effect);
                return;
            }
        }
        super.addEffect(effect);
    }

    @Override
    public String getRule() {
        return "{this} enters tapped. As it enters, choose a color"
                + (exceptColor != null ? " other than " + exceptColor.toLowerCase(Locale.ENGLISH) : "") + ".";
    }
}
