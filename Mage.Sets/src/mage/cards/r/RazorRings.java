package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorRings extends CardImpl {

    public RazorRings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Razor Rings deals 4 damage to target attacking or blocking creature. You gain life equal to the excess damage dealt this way.
        this.getSpellAbility().addEffect(new RazorRingsEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private RazorRings(final RazorRings card) {
        super(card);
    }

    @Override
    public RazorRings copy() {
        return new RazorRings(this);
    }
}

class RazorRingsEffect extends OneShotEffect {

    RazorRingsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target attacking or blocking creature. " +
                "You gain life equal to the excess damage dealt this way";
    }

    private RazorRingsEffect(final RazorRingsEffect effect) {
        super(effect);
    }

    @Override
    public RazorRingsEffect copy() {
        return new RazorRingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(4, source, game);
        if (excess > 0) {
            Optional.ofNullable(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.gainLife(excess, game, source));
        }
        return true;
    }
}
