
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

/**
 *
 * @author jeffwadsworth
 */
public final class BarrinsSpite extends CardImpl {

    public BarrinsSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Choose two target creatures controlled by the same player. Their controller chooses and sacrifices one of them. Return the other to its owner's hand.
        this.getSpellAbility().addEffect(new BarrinsSpiteEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    public BarrinsSpite(final BarrinsSpite card) {
        super(card);
    }

    @Override
    public BarrinsSpite copy() {
        return new BarrinsSpite(this);
    }
}

class BarrinsSpiteEffect extends OneShotEffect {

    public BarrinsSpiteEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target creatures controlled by the same player. Their controller chooses and sacrifices one of them. Return the other to its owner's hand";
    }

    public BarrinsSpiteEffect(final BarrinsSpiteEffect effect) {
        super(effect);
    }

    @Override
    public BarrinsSpiteEffect copy() {
        return new BarrinsSpiteEffect(this);
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
                    if ((count == 0
                            && controllerOfCreature.chooseUse(Outcome.Sacrifice, "Sacrifice " + creature.getLogName() + '?', source, game))
                            || (count == 1
                            && !sacrificeDone)) {
                        creature.sacrifice(source.getId(), game);
                        sacrificeDone = true;
                    } else {
                        creature.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                    count++;
                }
            }
            return true;
        }
        return false;
    }
}
