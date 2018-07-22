
package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public class AsEntersBattlefieldAbility extends StaticAbility {

    public AsEntersBattlefieldAbility(Effect effect) {
        this(effect, null);
    }

    public AsEntersBattlefieldAbility(Effect effect, String text) {
        super(Zone.ALL, new EntersBattlefieldEffect(effect, null, text, true, false));
    }

    public AsEntersBattlefieldAbility(final AsEntersBattlefieldAbility ability) {
        super(ability);
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
    public AsEntersBattlefieldAbility copy() {
        return new AsEntersBattlefieldAbility(this);
    }

    @Override
    public String getRule() {
        return "As {this} enters the battlefield, " + super.getRule();
    }
}
