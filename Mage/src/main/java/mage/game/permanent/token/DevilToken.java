
package mage.game.permanent.token;

import java.util.Arrays;
import java.util.Collections;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class DevilToken extends TokenImpl {

    public DevilToken() {
        super("Devil", "1/1 red Devil creature token with \"When this creature dies, it deals 1 damage to any target.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DEVIL);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // When this creature dies, it deals 1 damage to any target.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("it deals 1 damage to any target");
        Ability ability = new DiesSourceTriggeredAbility(effect);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("SOI", "WAR", "AFR", "MID");
    }

    public DevilToken(final DevilToken token) {
        super(token);
    }

    public DevilToken copy() {
        return new DevilToken(this);
    }
}
