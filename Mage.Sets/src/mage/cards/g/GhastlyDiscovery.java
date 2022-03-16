
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class GhastlyDiscovery extends CardImpl {

    public GhastlyDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new GhastlyDiscoveryEffect());

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.NONE));
    }

    private GhastlyDiscovery(final GhastlyDiscovery card) {
        super(card);
    }

    @Override
    public GhastlyDiscovery copy() {
        return new GhastlyDiscovery(this);
    }
}

class GhastlyDiscoveryEffect extends OneShotEffect {

    public GhastlyDiscoveryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw two cards, then discard a card";
    }

    public GhastlyDiscoveryEffect(final GhastlyDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public GhastlyDiscoveryEffect copy() {
        return new GhastlyDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game);
            controller.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
}
