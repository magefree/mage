package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterAllEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 * @author Viserion
 */
public final class PhyrexianVatmother extends CardImpl {

    public PhyrexianVatmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddPoisonCounterAllEffect(TargetController.YOU), TargetController.YOU, false
        ));
    }

    public PhyrexianVatmother(final PhyrexianVatmother card) {
        super(card);
    }

    @Override
    public PhyrexianVatmother copy() {
        return new PhyrexianVatmother(this);
    }
}
