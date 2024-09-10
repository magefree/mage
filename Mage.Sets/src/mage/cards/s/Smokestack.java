
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Smokestack extends CardImpl {

    public Smokestack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, you may put a soot counter on Smokestack.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SOOT.createInstance()), TargetController.YOU, true));

        // At the beginning of each player's upkeep, that player sacrifices a permanent for each soot counter on Smokestack.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SmokestackEffect(), TargetController.ANY, false));
    }

    private Smokestack(final Smokestack card) {
        super(card);
    }

    @Override
    public Smokestack copy() {
        return new Smokestack(this);
    }
}

class SmokestackEffect extends OneShotEffect {

    SmokestackEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "that player sacrifices a permanent for each soot counter on Smokestack";
    }

    private SmokestackEffect(final SmokestackEffect effect) {
        super(effect);
    }

    @Override
    public SmokestackEffect copy() {
        return new SmokestackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (activePlayer != null && sourcePermanent != null) {
            int count = sourcePermanent.getCounters(game).getCount(CounterType.SOOT);
            if (count > 0) {
                int amount = Math.min(count, game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT, activePlayer.getId(), game));
                TargetSacrifice target = new TargetSacrifice(amount, StaticFilters.FILTER_PERMANENT);
                //A spell or ability could have removed the only legal target this player
                //had, if thats the case this ability should fizzle.
                if (target.canChoose(activePlayer.getId(), source, game)) {
                    while (!target.isChosen(game) && target.canChoose(activePlayer.getId(), source, game) && activePlayer.canRespond()) {
                        activePlayer.choose(Outcome.Sacrifice, target, source, game);
                    }

                    for (int idx = 0; idx < target.getTargets().size(); idx++) {
                        Permanent permanent = game.getPermanent(target.getTargets().get(idx));

                        if (permanent != null) {
                            permanent.sacrifice(source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
