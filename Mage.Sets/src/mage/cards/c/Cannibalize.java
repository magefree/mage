
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

/**
 *
 * @author LevelX2
 */
public final class Cannibalize extends CardImpl {

    public Cannibalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose two target creatures controlled by the same player. Exile one of the creatures and put two +1/+1 counters on the other.
        this.getSpellAbility().addEffect(new CannibalizeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false));
    }

    private Cannibalize(final Cannibalize card) {
        super(card);
    }

    @Override
    public Cannibalize copy() {
        return new Cannibalize(this);
    }
}

class CannibalizeEffect extends OneShotEffect {

    public CannibalizeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose two target creatures controlled by the same player. Exile one of the creatures and put two +1/+1 counters on the other";
    }

    public CannibalizeEffect(final CannibalizeEffect effect) {
        super(effect);
    }

    @Override
    public CannibalizeEffect copy() {
        return new CannibalizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            boolean exileDone = false;
            int count = 0;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    if ((count == 0 && controller.chooseUse(Outcome.Exile, "Exile " + creature.getLogName() + '?', source, game))
                            || (count == 1 && !exileDone)) {
                        controller.moveCardToExileWithInfo(creature, null, "", source, game, Zone.BATTLEFIELD, true);
                        exileDone = true;
                    } else {
                        creature.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                        game.informPlayers("Added two +1/+1 counters on " + creature.getLogName());
                    }
                    count++;
                }
            }
            return true;
        }
        return false;
    }
}
