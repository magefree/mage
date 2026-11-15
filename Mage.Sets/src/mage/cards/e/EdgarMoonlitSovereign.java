package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EdgarMoonlitSovereign extends CardImpl {

    public EdgarMoonlitSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your end step, if you didn't cast a spell this turn, put two +1/+1 counters on Edgar.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
        ).withInterveningIf(EdgarMoonlitSovereignCondition.instance));

        // {4}{G}: Put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1),
            new ManaCostsImpl<>("{4}{G}")
        ));
    }

    private EdgarMoonlitSovereign(final EdgarMoonlitSovereign card) {
        super(card);
    }

    @Override
    public EdgarMoonlitSovereign copy() {
        return new EdgarMoonlitSovereign(this);
    }
}

enum EdgarMoonlitSovereignCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) == 0;
    }

    @Override
    public String toString() {
        return "you didn't cast a spell this turn";
    }
}
