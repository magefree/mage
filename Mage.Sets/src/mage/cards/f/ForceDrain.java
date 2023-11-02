package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class ForceDrain extends CardImpl {

    public ForceDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // ForceDrain deals 2 damage to any target. If player was dealt damage this way, you gain 2 life.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ForceDrainEffect());

        // Scry 1
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceDrain(final ForceDrain card) {
        super(card);
    }

    @Override
    public ForceDrain copy() {
        return new ForceDrain(this);
    }
}

class ForceDrainEffect extends OneShotEffect {

    public ForceDrainEffect() {
        super(Outcome.Damage);
        this.staticText = "ForceDrain deals 2 damage to any target. If player was dealt damage this way, you gain 2 life";
    }

    private ForceDrainEffect(final ForceDrainEffect effect) {
        super(effect);
    }

    @Override
    public ForceDrainEffect copy() {
        return new ForceDrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.damage(2, source.getId(), source, game, false, true);
                return true;
            }

            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                if (player.damage(2, source.getId(), source, game) > 0) {
                    controller.gainLife(2, game, source);
                }
                return true;
            }
        }
        return false;
    }
}
