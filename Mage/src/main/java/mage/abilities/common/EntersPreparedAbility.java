package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class EntersPreparedAbility extends StaticAbility {


    public EntersPreparedAbility() {
        super(Zone.ALL, new EntersBattlefieldEffect(new BecomePreparedSourceEffect()));
    }

    protected EntersPreparedAbility(final EntersPreparedAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "{this} enters prepared.";
    }

    @Override
    public EntersPreparedAbility copy() {
        return new EntersPreparedAbility(this);
    }
}
