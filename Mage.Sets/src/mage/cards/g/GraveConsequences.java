package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GraveConsequences extends CardImpl {

    public GraveConsequences(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Each player may exile any number of cards from their graveyard. Then each player loses 1 life for each card in their graveyard.
        this.getSpellAbility().addEffect(new GraveConsequencesEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private GraveConsequences(final GraveConsequences card) {
        super(card);
    }

    @Override
    public GraveConsequences copy() {
        return new GraveConsequences(this);
    }
}

class GraveConsequencesEffect extends OneShotEffect {

    GraveConsequencesEffect() {
        super(Outcome.Exile);
        staticText = "each player may exile any number of cards from their graveyard. " +
                "Then each player loses 1 life for each card in their graveyard";
    }

    private GraveConsequencesEffect(final GraveConsequencesEffect effect) {
        super(effect);
    }

    @Override
    public GraveConsequencesEffect copy() {
        return new GraveConsequencesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> players = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Player player : players) {
            TargetCard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE);
            target.setNotTarget(true);
            player.choose(outcome, target, source.getSourceId(), game);
            Cards cards = new CardsImpl(target.getTargets());
            if (!cards.isEmpty()) {
                player.moveCards(cards, Zone.EXILED, source, game);
            }
        }
        for (Player player : players) {
            if (!player.getGraveyard().isEmpty()) {
                player.loseLife(player.getLife(), game, source, false);
            }
        }
        return true;
    }
}
