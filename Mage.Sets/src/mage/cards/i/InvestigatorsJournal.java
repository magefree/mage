package mage.cards.i;

import java.util.HashMap;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class InvestigatorsJournal extends CardImpl {

    public InvestigatorsJournal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.CLUE);

        // Investigator's Journal enters the battlefield with a number of suspect counters on it equal to the greatest number of creatures a player controls.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SUSPECT.createInstance(), InvestigatorsJournalValue.instance, false),
                "with a number of suspect counters on it equal to the greatest number of creatures a player controls"
        ));

        // {2}, {T}, Remove a suspect counter from Investigator's Journal: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.SUSPECT.createInstance()));
        this.addAbility(ability);

        // {2}, Sacrifice Investigator's Journal: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private InvestigatorsJournal(final InvestigatorsJournal card) {
        super(card);
    }

    @Override
    public InvestigatorsJournal copy() {
        return new InvestigatorsJournal(this);
    }
}

enum InvestigatorsJournalValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int greatestCreatureCount = 0;

        for (UUID playerId : game.getState().getPlayersInRange(sourceAbility.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            greatestCreatureCount = Math.max(
                    greatestCreatureCount,
                    game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game));
        }

        return greatestCreatureCount;
    }

    @Override
    public InvestigatorsJournalValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest number of creatures a player controls";
    }
}
