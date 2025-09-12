package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wisecrack extends CardImpl {

    public Wisecrack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target creature deals damage equal to its power to itself. If that creature is attacking, Wisecrack deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new WisecrackEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Wisecrack(final Wisecrack card) {
        super(card);
    }

    @Override
    public Wisecrack copy() {
        return new Wisecrack(this);
    }
}

class WisecrackEffect extends OneShotEffect {

    WisecrackEffect() {
        super(Outcome.Benefit);
        staticText = "target creature deals damage equal to its power to itself. " +
                "If that creature is attacking, {this} deals 2 damage to that creature's controller";
    }

    private WisecrackEffect(final WisecrackEffect effect) {
        super(effect);
    }

    @Override
    public WisecrackEffect copy() {
        return new WisecrackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        if (permanent.isAttacking()) {
            Optional.ofNullable(permanent)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .map(player -> player.damage(2, source, game));
        }
        return true;
    }
}
