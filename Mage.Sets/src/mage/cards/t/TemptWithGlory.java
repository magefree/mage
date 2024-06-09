
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TemptWithGlory extends CardImpl {

    public TemptWithGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Tempting offer - Put a +1/+1 counter on each creature you control. Each opponent may put a +1/+1 counter on each creature they control. For each opponent who does, put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new TemptWithGloryEffect());
    }

    private TemptWithGlory(final TemptWithGlory card) {
        super(card);
    }

    @Override
    public TemptWithGlory copy() {
        return new TemptWithGlory(this);
    }
}

class TemptWithGloryEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final Counter counter = CounterType.P1P1.createInstance();

    public TemptWithGloryEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "<i>Tempting offer</i> &mdash; Put a +1/+1 counter on each creature you control. Each opponent may put a +1/+1 counter on each creature they control. For each opponent who does, put a +1/+1 counter on each creature you control";
    }

    private TemptWithGloryEffect(final TemptWithGloryEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithGloryEffect copy() {
        return new TemptWithGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            addCounterToEachCreature(controller.getId(), counter, source, game);
            int opponentsAddedCounters = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, "Put a +1/+1 counter on each creature you control?", source, game)) {
                        opponentsAddedCounters++;
                        addCounterToEachCreature(playerId, counter, source, game);
                        game.informPlayers(opponent.getLogName() + " added a +1/+1 counter on each of its creatures");
                    }
                }
            }
            if (opponentsAddedCounters > 0) {
                addCounterToEachCreature(controller.getId(), CounterType.P1P1.createInstance(opponentsAddedCounters), source, game);
            }
            return true;
        }

        return false;
    }

    private void addCounterToEachCreature(UUID playerId, Counter counter, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
            permanent.addCounters(counter, playerId, source, game);
        }
    }
}
