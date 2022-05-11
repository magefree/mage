
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SerpentGeneratorSnakeToken extends TokenImpl {

    public SerpentGeneratorSnakeToken() {
        super("Snake Token", "1/1 colorless Snake artifact creature token with \"Whenever this creature deals damage to a player, that player gets a poison counter.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("that player gets a poison counter");
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(effect, false, true));
    }

    public SerpentGeneratorSnakeToken(final SerpentGeneratorSnakeToken token) {
        super(token);
    }

    public SerpentGeneratorSnakeToken copy() {
        return new SerpentGeneratorSnakeToken(this);
    }

}
