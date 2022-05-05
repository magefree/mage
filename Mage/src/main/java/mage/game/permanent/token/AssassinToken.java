

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;

/**
 *
 * @author spjspj
 */
public final class AssassinToken extends TokenImpl {

    public AssassinToken() {
        super("Assassin Token", "1/1 black Assassin creature tokens with \"Whenever this creature deals combat damage to a player, that player loses the game.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseGameTargetPlayerEffect(), false, true));
    }

    public AssassinToken(final AssassinToken token) {
        super(token);
    }

    public AssassinToken copy() {
        return new AssassinToken(this);
    }
}

