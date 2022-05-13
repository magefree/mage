package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class PlanarOverlay extends CardImpl {

    public PlanarOverlay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player chooses a land they control of each basic land type. Return those lands to their owners' hands.
        this.getSpellAbility().addEffect(new PlanarOverlayEffect());
    }

    private PlanarOverlay(final PlanarOverlay card) {
        super(card);
    }

    @Override
    public PlanarOverlay copy() {
        return new PlanarOverlay(this);
    }
}

class PlanarOverlayEffect extends OneShotEffect {

    public PlanarOverlayEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Each player chooses a land they control of each basic land type. Return those lands to their owners' hands";
    }

    public PlanarOverlayEffect(final PlanarOverlayEffect effect) {
        super(effect);
    }

    @Override
    public PlanarOverlayEffect copy() {
        return new PlanarOverlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> lands = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (SubType landName : SubType.getBasicLands()) {
                        FilterLandPermanent filter = new FilterLandPermanent(landName + " to return to hand");
                        filter.add(landName.getPredicate());
                        filter.add(TargetController.YOU.getControllerPredicate());
                        Target target = new TargetLandPermanent(1, 1, filter, true);
                        if (target.canChoose(player.getId(), source, game)) {
                            player.chooseTarget(outcome, target, source, game);
                            lands.add(game.getPermanent(target.getFirstTarget()));
                        }
                    }
                }
            }
            controller.moveCards(lands, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
