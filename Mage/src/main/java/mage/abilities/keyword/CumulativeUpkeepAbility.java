package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class CumulativeUpkeepAbility extends BeginningOfUpkeepTriggeredAbility {

    private Cost cumulativeCost;

    public CumulativeUpkeepAbility(Cost cumulativeCost) {
        super(new AddCountersSourceEffect(CounterType.AGE.createInstance()), TargetController.YOU, false);
        this.addEffect(new CumulativeUpkeepEffect(cumulativeCost));
        this.cumulativeCost = cumulativeCost;
    }

    public CumulativeUpkeepAbility(final CumulativeUpkeepAbility ability) {
        super(ability);
        this.cumulativeCost = ability.cumulativeCost.copy();
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new CumulativeUpkeepAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Cumulative upkeep");
        if (!(cumulativeCost instanceof ManaCost || cumulativeCost instanceof OrCost)) {
            sb.append("&mdash;");
        } else {
            sb.append(' ');
        }
        sb.append(cumulativeCost.getText());
        return sb.toString();
    }
}

class CumulativeUpkeepEffect extends OneShotEffect {

    private final Cost cumulativeCost;

    CumulativeUpkeepEffect(Cost cumulativeCost) {
        super(Outcome.Sacrifice);
        this.cumulativeCost = cumulativeCost;
    }

    CumulativeUpkeepEffect(final CumulativeUpkeepEffect effect) {
        super(effect);
        this.cumulativeCost = effect.cumulativeCost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            int ageCounter = permanent.getCounters(game).getCount(CounterType.AGE);
            if (cumulativeCost instanceof ManaCost) {
                ManaCostsImpl totalCost = new ManaCostsImpl<>();
                for (int i = 0; i < ageCounter; i++) {
                    totalCost.add((ManaCost) cumulativeCost.copy());
                }
                if (player.chooseUse(Outcome.Benefit, "Pay " + totalCost.getText() + '?', source, game)) {
                    totalCost.clearPaid();
                    if (totalCost.payOrRollback(source, game, source.getSourceId(), source.getControllerId())) {
                        game.fireEvent(new ManaEvent(EventType.PAID_CUMULATIVE_UPKEEP, permanent.getId(), permanent.getId(), player.getId(), totalCost.getUsedManaToPay()));
                        return true;
                    }
                }
                game.fireEvent(new GameEvent(EventType.DIDNT_PAY_CUMULATIVE_UPKEEP, permanent.getId(), permanent.getId(), player.getId(), ageCounter, false));
                permanent.sacrifice(source.getSourceId(), game);
                return true;
            } else {
                CostsImpl<Cost> totalCost = new CostsImpl<>();
                for (int i = 0; i < ageCounter; i++) {
                    totalCost.add(cumulativeCost.copy());
                }
                if (player.chooseUse(Outcome.Benefit, totalCost.getText() + '?', source, game)) {
                    totalCost.clearPaid();
                    int bookmark = game.bookmarkState();
                    if (totalCost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                        game.fireEvent(new GameEvent(EventType.PAID_CUMULATIVE_UPKEEP, permanent.getId(), permanent.getId(), player.getId(), ageCounter, false));
                        return true;
                    } else {
                        game.restoreState(bookmark, source.getRule());
                    }
                }
                game.fireEvent(new GameEvent(EventType.DIDNT_PAY_CUMULATIVE_UPKEEP, permanent.getId(), permanent.getId(), player.getId(), ageCounter, false));
                permanent.sacrifice(source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public CumulativeUpkeepEffect copy() {
        return new CumulativeUpkeepEffect(this);
    }
}
