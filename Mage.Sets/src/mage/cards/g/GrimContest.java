
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GrimContest extends CardImpl {

    public GrimContest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{G}");

        // Choose target creature you control and target creature an opponent controls. Each of those creatures deals damage equal to its toughness to the other.
        this.getSpellAbility().addEffect(new GrimContestEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private GrimContest(final GrimContest card) {
        super(card);
    }

    @Override
    public GrimContest copy() {
        return new GrimContest(this);
    }
}

class GrimContestEffect extends OneShotEffect {

    public GrimContestEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose target creature you control and target creature an opponent controls. Each of those creatures deals damage equal to its toughness to the other";
    }

    public GrimContestEffect(final GrimContestEffect effect) {
        super(effect);
    }

    @Override
    public GrimContestEffect copy() {
        return new GrimContestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent creature1 = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature1 != null && creature2 != null) {
                if (creature1.isCreature(game) && creature2.isCreature(game)) {
                    creature1.damage(creature2.getToughness().getValue(), creature2.getId(), source, game, false, true);
                    game.informPlayers(creature2.getLogName() + " deals " + creature2.getToughness().getValue() + " damage to " + creature1.getLogName());
                    creature2.damage(creature1.getToughness().getValue(), creature1.getId(), source, game, false, true);
                    game.informPlayers(creature1.getLogName() + " deals " + creature1.getToughness().getValue() + " damage to " + creature2.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
