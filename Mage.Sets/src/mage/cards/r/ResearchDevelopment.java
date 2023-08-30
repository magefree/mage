package mage.cards.r;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.ResearchDevelopmentToken;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author magenoxx
 */
public final class ResearchDevelopment extends SplitCard {

    public ResearchDevelopment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}", "{3}{U}{R}", SpellAbilityType.SPLIT);

        // Choose up to four cards you own from outside the game and shuffle them into your library.
        getLeftHalfCard().getSpellAbility().addEffect(new ResearchEffect());
        getLeftHalfCard().getSpellAbility().addHint(OpenSideboardHint.instance);

        // Create a 3/1 red Elemental creature token unless any opponent has you draw a card. Repeat this process two more times.
        getRightHalfCard().getSpellAbility().addEffect(new DevelopmentEffect());
    }

    private ResearchDevelopment(final ResearchDevelopment card) {
        super(card);
    }

    @Override
    public ResearchDevelopment copy() {
        return new ResearchDevelopment(this);
    }
}

class ResearchEffect extends OneShotEffect {

    public ResearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle up to four cards you own from outside the game into your library";
    }

    public ResearchEffect(final ResearchEffect effect) {
        super(effect);
    }

    @Override
    public ResearchEffect copy() {
        return new ResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.Benefit, staticText + "?", source, game)) {
                Cards cards = controller.getSideboard();
                if (cards.isEmpty()) {
                    game.informPlayer(controller, "You have no cards outside the game.");
                    return true;
                }
                TargetCard target = new TargetCard(0, 4, Zone.OUTSIDE, new FilterCard("cards you own from outside the game"));
                target.setNotTarget(true);
                if (controller.choose(Outcome.Benefit, controller.getSideboard(), target, source, game)) {
                    controller.shuffleCardsToLibrary(new CardsImpl(target.getTargets()), game, source);
                }
            }
            return true;
        }

        return false;
    }
}

class DevelopmentEffect extends OneShotEffect {

    public DevelopmentEffect() {
        super(Outcome.Benefit);
        staticText = "Create a 3/1 red Elemental creature token unless any opponent has you draw a card. Repeat this process two more times.";
    }

    DevelopmentEffect(final DevelopmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (int i = 0; i < 3; i++) {
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                boolean putToken = true;
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && opponent.chooseUse(Outcome.Detriment,
                            "Allow " + player.getLogName() + " to draw a card instead? (" + (i + 1) + ')', source, game)) {
                        game.informPlayers(opponent.getLogName() + " had chosen to let " + player.getLogName() + " draw a card.");
                        player.drawCards(1, source, game);
                        putToken = false;
                        break;
                    }
                }
                if (putToken) {
                    new CreateTokenEffect(new ResearchDevelopmentToken()).apply(game, source);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public DevelopmentEffect copy() {
        return new DevelopmentEffect(this);
    }

}
