package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.BecomesAttachedToCreatureSourceEffect;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * Based on {@link AsEntersBattlefieldAbility}
 * This allows rule wording such as "as {this} becomes attached to a creature...".
 * See [[Dinosaur Headdress]].
 * For this, we do not want a trigger, so a replacement effect is used to add
 * one or more effects to the attach effect.
 *
 * @author DominionSpy
 */
public class AsBecomesAttachedToCreatureSourceAbility extends StaticAbility {

    public AsBecomesAttachedToCreatureSourceAbility(Effect effect) {
        this(effect, null);
    }

    public AsBecomesAttachedToCreatureSourceAbility(Effect effect, String text) {
        super(Zone.BATTLEFIELD, new BecomesAttachedToCreatureSourceEffect(effect, null, text, false));
    }

    protected AsBecomesAttachedToCreatureSourceAbility(final AsBecomesAttachedToCreatureSourceAbility ability) {
        super(ability);
    }

    @Override
    public AsBecomesAttachedToCreatureSourceAbility copy() {
        return new AsBecomesAttachedToCreatureSourceAbility(this);
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect attachEffect = this.getEffects().get(0);
            if (attachEffect instanceof BecomesAttachedToCreatureSourceEffect) {
                ((BecomesAttachedToCreatureSourceEffect) attachEffect).addEffect(effect);
                return;
            }
        }
        super.addEffect(effect);
    }

    @Override
    public String getRule() {
        return "As {this} becomes attached to a creature, " + super.getRule();
    }
}
