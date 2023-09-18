package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RendingFlame extends CardImpl {

    public RendingFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Rending Flame deals 5 damage to target creature or planeswalker. If that permanent is a Spirit, Rending Flame also deals 2 damage to that permanent's controller.
        this.getSpellAbility().addEffect(new RendingFlameEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private RendingFlame(final RendingFlame card) {
        super(card);
    }

    @Override
    public RendingFlame copy() {
        return new RendingFlame(this);
    }
}

class RendingFlameEffect extends OneShotEffect {

    RendingFlameEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to target creature or planeswalker. " +
                "If that permanent is a Spirit, {this} also deals 2 damage to that permanent's controller";
    }

    private RendingFlameEffect(final RendingFlameEffect effect) {
        super(effect);
    }

    @Override
    public RendingFlameEffect copy() {
        return new RendingFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(5, source, game);
        if (!permanent.hasSubtype(SubType.SPIRIT, game)) {
            return true;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(2, source, game);
        }
        return true;
    }
}
