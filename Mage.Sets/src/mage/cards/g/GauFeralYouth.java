package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.watchers.common.CardsLeftGraveyardWatcher;

/**
 * @author balazskristof
 */
public final class GauFeralYouth extends CardImpl {

    public GauFeralYouth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rage -- Whenever Gau attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).withFlavorWord("Rage"));

        // At the beginning of each end step, if a card left your graveyard this turn, Gau deals damage equal to its power to each opponent.
        this.addAbility(
                new BeginningOfEndStepTriggeredAbility(
                        TargetController.ANY,
                        new DamagePlayersEffect(SourcePermanentPowerValue.NOT_NEGATIVE, TargetController.OPPONENT)
                                .setText("{this} deals damage equal to its power to each opponent"),
                        false,
                        GauFeralYouthCondition.instance
                ),
                new CardsLeftGraveyardWatcher()
        );
    }

    private GauFeralYouth(final GauFeralYouth card) {
        super(card);
    }

    @Override
    public GauFeralYouth copy() {
        return new GauFeralYouth(this);
    }
}

enum GauFeralYouthCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(CardsLeftGraveyardWatcher.class)
                .getCardsThatLeftGraveyard(source.getControllerId(), game)
                .isEmpty();
    }

    @Override
    public String toString() {
        return "if a card left your graveyard this turn";
    }
}
