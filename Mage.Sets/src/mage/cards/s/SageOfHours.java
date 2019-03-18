
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class SageOfHours extends CardImpl {

    public SageOfHours(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Sage of Hours, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
        // Remove all +1/+1 counters from Sage of Hours: For each five counters removed this way, take an extra turn after this one.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SageOfHoursEffect(), new SageOfHoursCost()));
        
    }

    public SageOfHours(final SageOfHours card) {
        super(card);
    }

    @Override
    public SageOfHours copy() {
        return new SageOfHours(this);
    }
}

class SageOfHoursCost extends CostImpl {

    private int removedCounters;

    public SageOfHoursCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all +1/+1 counters from {this}";
    }

    public SageOfHoursCost(SageOfHoursCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.P1P1.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public SageOfHoursCost copy() {
        return new SageOfHoursCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
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
                if (cost instanceof SageOfHoursCost) {
                    countersRemoved = ((SageOfHoursCost) cost).getRemovedCounters();
                }
            }
            int turns = countersRemoved / 5;
            for (int i = 0; i < turns; i++) {
                game.getState().getTurnMods().add(new TurnMod(player.getId(), false));
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
