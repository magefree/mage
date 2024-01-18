package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.AttachesToEffect;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

public class AsAttachesToCreatureAbility extends StaticAbility {

    public AsAttachesToCreatureAbility(Effect effect) {
        this(effect, null);
    }

    public AsAttachesToCreatureAbility(Effect effect, String text) {
        super(Zone.BATTLEFIELD, new AttachesToEffect(effect, null, text, false));
    }

    protected AsAttachesToCreatureAbility(final AsAttachesToCreatureAbility ability) {
        super(ability);
    }

    @Override
    public AsAttachesToCreatureAbility copy() {
        return new AsAttachesToCreatureAbility(this);
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect attachEffect = this.getEffects().get(0);
            if (attachEffect instanceof AttachesToEffect) {
                ((AttachesToEffect) attachEffect).addEffect(effect);
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
