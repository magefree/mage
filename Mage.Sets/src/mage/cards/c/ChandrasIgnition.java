package mage.cards.c;

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
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChandrasIgnition extends CardImpl {

    public ChandrasIgnition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Target creature you control deals damage equal to its power to each other creature and each opponent.
        this.getSpellAbility().addEffect(new ChandrasIgnitionEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ChandrasIgnition(final ChandrasIgnition card) {
        super(card);
    }

    @Override
    public ChandrasIgnition copy() {
        return new ChandrasIgnition(this);
    }
}

class ChandrasIgnitionEffect extends OneShotEffect {

    public ChandrasIgnitionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature you control deals damage equal to its power to each other creature and each opponent";
    }

    public ChandrasIgnitionEffect(final ChandrasIgnitionEffect effect) {
        super(effect);
    }

    @Override
    public ChandrasIgnitionEffect copy() {
        return new ChandrasIgnitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null && targetCreature.getPower().getValue() > 0) {
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                if (!creature.getId().equals(targetCreature.getId())) {
                    creature.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game, false, true);
                }
            }
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game);
                }
            }
        }
        return true;
    }
}
