package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class LastDitchEffort extends CardImpl {

    public LastDitchEffort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Sacrifice any number of creatures. Last-Ditch Effort deals that much damage to any target.
        this.getSpellAbility().addEffect(new LastDitchEffortEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private LastDitchEffort(final LastDitchEffort card) {
        super(card);
    }

    @Override
    public LastDitchEffort copy() {
        return new LastDitchEffort(this);
    }
}

class LastDitchEffortEffect extends OneShotEffect {

    LastDitchEffortEffect() {
        super(Outcome.Damage);
        this.staticText = "Sacrifice any number of creatures. {this} deals that much damage to any target";
    }

    private LastDitchEffortEffect(final LastDitchEffortEffect effect) {
        super(effect);
    }

    @Override
    public LastDitchEffortEffect copy() {
        return new LastDitchEffortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetSacrifice(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_CREATURES);
            player.choose(Outcome.Sacrifice, target, source, game);
            int numSacrificed = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null && permanent.sacrifice(source, game)) {
                    numSacrificed++;
                }
            }
            if (numSacrificed > 0) {
                UUID uuid = this.getTargetPointer().getFirst(game, source);
                Permanent permanent = game.getPermanent(uuid);
                Player opponent = game.getPlayer(uuid);
                if (permanent != null) {
                    permanent.damage(numSacrificed, source.getSourceId(), source, game, false, true);
                }
                if (opponent != null) {
                    opponent.damage(numSacrificed, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
