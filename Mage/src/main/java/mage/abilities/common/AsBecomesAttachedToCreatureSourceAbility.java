package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.BecomesAttachedToCreatureSourceEffect;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * Based on {@link mage.abilities.common.AsEntersBattlefieldAbility}.
 * This allows rule wording such as "as {this} becomes attached to a creature..."
 * For this, there should not be a trigger, as in the case of the wording "when..."
 * As per rule 603.6d, this should be a static ability.
 * See [[Sanctuary Blade]].
 *
 * @author DominionSpy
 */
public class AsBecomesAttachedToCreatureSourceAbility extends StaticAbility {

    public AsBecomesAttachedToCreatureSourceAbility(Effect effect) {
        this(effect, null);
    }

    public AsBecomesAttachedToCreatureSourceAbility(Effect effect, String text) {
        super(Zone.BATTLEFIELD, new BecomesAttachedToCreatureSourceEffect(effect, null, text));
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
