package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class HomingLightning extends CardImpl {

    public HomingLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // Homing Lightning deals 4 damage to target creature and each other creature with the same name as that creature.
        this.getSpellAbility().addEffect(new HomingLightningEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HomingLightning(final HomingLightning card) {
        super(card);
    }

    @Override
    public HomingLightning copy() {
        return new HomingLightning(this);
    }
}

class HomingLightningEffect extends OneShotEffect {

    HomingLightningEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to target creature and each other creature with the same name as that creature";
    }

    private HomingLightningEffect(final HomingLightningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        Set<Permanent> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        for (Permanent creature : set) {
            creature.damage(4, source, game);
        }
        return true;
    }

    @Override
    public HomingLightningEffect copy() {
        return new HomingLightningEffect(this);
    }
}
