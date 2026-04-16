package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.condition.common.WasCardExiledThisTurnCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 * @author Susucr
 */
public final class AshiokWickedManipulatorNightmareToken extends TokenImpl {

    /**
     * /!\ You need to add CardsExiledThisTurnWatcher to any card using this token
     */
    public AshiokWickedManipulatorNightmareToken() {
        super("Nightmare Token", "1/1 black Nightmare creature token with \"At the beginning of combat on your turn, if a card was put into exile this turn, put a +1/+1 counter on this token.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.NIGHTMARE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(WasCardExiledThisTurnCondition.instance).addHint(WasCardExiledThisTurnCondition.getHint()));
    }

    private AshiokWickedManipulatorNightmareToken(final AshiokWickedManipulatorNightmareToken token) {
        super(token);
    }

    public AshiokWickedManipulatorNightmareToken copy() {
        return new AshiokWickedManipulatorNightmareToken(this);
    }
}
