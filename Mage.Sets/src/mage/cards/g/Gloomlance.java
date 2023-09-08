
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Gloomlance extends CardImpl {

    public Gloomlance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Destroy target creature. If that creature was green or white, its controller discards a card.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GloomlanceEffect());

    }

    private Gloomlance(final Gloomlance card) {
        super(card);
    }

    @Override
    public Gloomlance copy() {
        return new Gloomlance(this);
    }
}

class GloomlanceEffect extends OneShotEffect {

    public GloomlanceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. If that creature was green or white, its controller discards a card";
    }

    private GloomlanceEffect(final GloomlanceEffect effect) {
        super(effect);
    }

    @Override
    public GloomlanceEffect copy() {
        return new GloomlanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            Player targetController = game.getPlayer(targetCreature.getControllerId());
            targetCreature.destroy(source, game, false);
            Permanent destroyedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (destroyedCreature.getColor(game).isGreen()
                    || destroyedCreature.getColor(game).isWhite()) {
                if(targetController != null) {
                    targetController.discard(1, false, false, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
