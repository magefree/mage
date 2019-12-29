
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Retribution extends CardImpl {

    public Retribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose two target creatures an opponent controls. That player chooses and sacrifices one of those creatures. Put a -1/-1 counter on the other.
        this.getSpellAbility().addEffect(new RetributionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentOpponentSameController(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    public Retribution(final Retribution card) {
        super(card);
    }

    @Override
    public Retribution copy() {
        return new Retribution(this);
    }
}

class RetributionEffect extends OneShotEffect {

    public RetributionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target creatures an opponent controls. That player chooses and sacrifices one of those creatures. Put a -1/-1 counter on the other";
    }

    public RetributionEffect(final RetributionEffect effect) {
        super(effect);
    }

    @Override
    public RetributionEffect copy() {
        return new RetributionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            boolean sacrificeDone = false;
            int count = 0;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    Player controllerOfCreature = game.getPlayer(creature.getControllerId());
                    if ((count == 0 && controllerOfCreature != null
                            && controllerOfCreature.chooseUse(Outcome.Sacrifice, "Sacrifice " + creature.getLogName() + '?', source, game))
                            || (count == 1
                            && !sacrificeDone)) {
                        creature.sacrifice(source.getId(), game);
                        sacrificeDone = true;
                    } else {
                        creature.addCounters(CounterType.M1M1.createInstance(), source, game);
                    }
                    count++;
                }
            }
            return true;
        }
        return false;
    }
}

class TargetCreaturePermanentOpponentSameController extends TargetCreaturePermanent {

    public TargetCreaturePermanentOpponentSameController(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetCreaturePermanentOpponentSameController(final TargetCreaturePermanentOpponentSameController target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent firstTargetPermanent = game.getPermanent(id);
            if (firstTargetPermanent != null
                    && game.getOpponents(controllerId).contains(firstTargetPermanent.getControllerId())) {
                for (UUID targetId : getTargets()) {
                    Permanent targetPermanent = game.getPermanent(targetId);
                    if (targetPermanent != null) {
                        if (!firstTargetPermanent.getId().equals(targetPermanent.getId())) {
                            if (!firstTargetPermanent.isControlledBy(targetPermanent.getOwnerId())) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TargetCreaturePermanentOpponentSameController copy() {
        return new TargetCreaturePermanentOpponentSameController(this);
    }
}
