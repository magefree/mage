package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SageOfHours extends CardImpl {

    public SageOfHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Sage of Hours, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
        // Remove all +1/+1 counters from Sage of Hours: For each five counters removed this way, take an extra turn after this one.
        this.addAbility(new SimpleActivatedAbility(new SageOfHoursEffect(), new RemoveAllCountersSourceCost(CounterType.P1P1)));
    }

    private SageOfHours(final SageOfHours card) {
        super(card);
    }

    @Override
    public SageOfHours copy() {
        return new SageOfHours(this);
    }
}

class SageOfHoursEffect extends OneShotEffect {

    public SageOfHoursEffect() {
        super(Outcome.AIDontUseIt); // AI uses it endless therefore deactivated
        this.staticText = "For each five counters removed this way, take an extra turn after this one";
    }

    public SageOfHoursEffect(final SageOfHoursEffect effect) {
        super(effect);
    }

    @Override
    public SageOfHoursEffect copy() {
        return new SageOfHoursEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int countersRemoved = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveAllCountersSourceCost) {
                    countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
                }
            }
            int turns = countersRemoved / 5;
            for (int i = 0; i < turns; i++) {
                game.getState().getTurnMods().add(new TurnMod(player.getId()).withExtraTurn());
            }
            game.informPlayers("Removed " + countersRemoved +
                    " +1/+1 counters: " + player.getLogName() + " takes " +
                    CardUtil.numberToText(turns, "an") +
                    (turns > 1 ? " extra turns " : " extra turn ") +
                    "after this one");
            return true;
        }
        return false;
    }
}
