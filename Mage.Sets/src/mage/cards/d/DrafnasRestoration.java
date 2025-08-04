
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class DrafnasRestoration extends CardImpl {

    public DrafnasRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Put any number of target artifact cards from target player's graveyard on top of their library in any order.
        this.getSpellAbility().addEffect(new DrafnasRestorationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new DrafnasRestorationTarget());
    }

    private DrafnasRestoration(final DrafnasRestoration card) {
        super(card);
    }

    @Override
    public DrafnasRestoration copy() {
        return new DrafnasRestoration(this);
    }
}

class DrafnasRestorationTarget extends TargetCardInGraveyard {

    DrafnasRestorationTarget() {
        super(0, Integer.MAX_VALUE, new FilterArtifactCard("any number of artifact cards from that player's graveyard"));
    }

    private DrafnasRestorationTarget(final DrafnasRestorationTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Player controller = game.getPlayer(sourceControllerId);
        if (controller == null) {
            return possibleTargets;
        }

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        game.getState().getPlayersInRange(sourceControllerId, game, true).stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .flatMap(player -> player.getGraveyard().getCards(filter, sourceControllerId, source, game).stream())
                .forEach(card -> {
                    if (targetPlayer == null) {
                        // playable or not selected - use any
                        possibleTargets.add(card.getId());
                    } else {
                        // selected, filter by player
                        if (targetPlayer.getId().equals(card.getControllerOrOwnerId())) {
                            possibleTargets.add(card.getId());
                        }
                    }
                });

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public DrafnasRestorationTarget copy() {
        return new DrafnasRestorationTarget(this);
    }
}

class DrafnasRestorationEffect extends OneShotEffect {

    DrafnasRestorationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put any number of target artifact cards from target player's graveyard on top of their library in any order.";
    }

    private DrafnasRestorationEffect(final DrafnasRestorationEffect effect) {
        super(effect);
    }

    @Override
    public DrafnasRestorationEffect copy() {
        return new DrafnasRestorationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(source.getTargets().get(1).getTargets());
            controller.putCardsOnTopOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
