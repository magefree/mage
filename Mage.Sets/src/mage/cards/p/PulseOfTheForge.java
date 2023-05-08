
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class PulseOfTheForge extends CardImpl {

    public PulseOfTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Pulse of the Forge deals 4 damage to target player. Then if that player has more life than you, return Pulse of the Forge to its owner's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new PulseOfTheForgeReturnToHandEffect());
    }

    private PulseOfTheForge(final PulseOfTheForge card) {
        super(card);
    }

    @Override
    public PulseOfTheForge copy() {
        return new PulseOfTheForge(this);
    }
}

class PulseOfTheForgeReturnToHandEffect extends OneShotEffect {

    PulseOfTheForgeReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if that player or that planeswalker's controller has more life than you, return {this} to its owner's hand";
    }

    PulseOfTheForgeReturnToHandEffect(final PulseOfTheForgeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheForgeReturnToHandEffect copy() {
        return new PulseOfTheForgeReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayerOrPlaneswalkerController(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null && player.getLife() > controller.getLife()) {
                Card card = game.getCard(source.getSourceId());
                controller.moveCards(card, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
