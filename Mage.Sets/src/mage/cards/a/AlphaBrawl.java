package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public final class AlphaBrawl extends CardImpl {

    public AlphaBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{R}{R}");

        // Target creature an opponent controls deals damage equal to its power to each other creature that player controls, then each of those creatures deals damage equal to its power to that creature.
        this.getSpellAbility().addEffect(new AlphaBrawlEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

    }

    private AlphaBrawl(final AlphaBrawl card) {
        super(card);
    }

    @Override
    public AlphaBrawl copy() {
        return new AlphaBrawl(this);
    }
}

class AlphaBrawlEffect extends OneShotEffect {

    public AlphaBrawlEffect() {
        super(Outcome.Damage);
        staticText = "Target creature an opponent controls deals damage equal to its power to each other creature that player controls, then each of those creatures deals damage equal to its power to that creature";
    }

    public AlphaBrawlEffect(final AlphaBrawlEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature that player controls");
        filter.add(Predicates.not(new PermanentIdPredicate(targetId)));
        Permanent creature = game.getPermanent(targetId);
        if (creature != null) {
            Player player = game.getPlayer(creature.getControllerId());
            if (player != null) {
                int power = creature.getPower().getValue();
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    perm.damage(power, creature.getId(), source, game, false, true);
                }
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    creature.damage(perm.getPower().getValue(), perm.getId(), source, game, false, true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public AlphaBrawlEffect copy() {
        return new AlphaBrawlEffect(this);
    }

}
