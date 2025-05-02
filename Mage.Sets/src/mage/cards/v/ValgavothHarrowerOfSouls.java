package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.LoseLifeFirstTimeEachTurnTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ValgavothHarrowerOfSouls extends CardImpl {

    public ValgavothHarrowerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // Whenever an opponent loses life for the first time during each of their turns, put a +1/+1 counter on Valgavoth, Harrower of Souls and draw a card.
        this.addAbility(new ValgavothHarrowerOfSoulsTriggeredAbility());

    }

    private ValgavothHarrowerOfSouls(final ValgavothHarrowerOfSouls card) {
        super(card);
    }

    @Override
    public ValgavothHarrowerOfSouls copy() {
        return new ValgavothHarrowerOfSouls(this);
    }
}

class ValgavothHarrowerOfSoulsTriggeredAbility extends LoseLifeFirstTimeEachTurnTriggeredAbility {

    ValgavothHarrowerOfSoulsTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.OPPONENT, false, false);
        this.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        setTriggerPhrase("Whenever an opponent loses life for the first time during each of their turns, ");
    }

    private ValgavothHarrowerOfSoulsTriggeredAbility(final ValgavothHarrowerOfSoulsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ValgavothHarrowerOfSoulsTriggeredAbility copy() {
        return new ValgavothHarrowerOfSoulsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(event.getTargetId()) && super.checkTrigger(event, game);
    }

}
