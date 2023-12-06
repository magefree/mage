package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class SandGolem extends CardImpl {

    public SandGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When a spell or ability an opponent controls causes you to discard Sand Golem,
        // return Sand Golem from your graveyard to the battlefield with a +1/+1 counter on it at the beginning of the next end step.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.P1P1.createInstance(), false)));
        effect.setText("return {this} from your graveyard to the battlefield with a +1/+1 counter on it at the beginning of the next end step");

        this.addAbility(new DiscardedByOpponentTriggeredAbility(effect, true));
    }

    private SandGolem(final SandGolem card) {
        super(card);
    }

    @Override
    public SandGolem copy() {
        return new SandGolem(this);
    }
}
