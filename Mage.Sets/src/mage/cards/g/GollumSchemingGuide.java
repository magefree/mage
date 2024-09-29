package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GollumSchemingGuide extends CardImpl {

    public GollumSchemingGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Gollum, Scheming Guide attacks, look at the top two cards of your library, put them back in any order, then choose land or nonland. An opponent guesses whether the top card of your library is the chosen kind. Reveal that card. If they guessed right, remove Gollum from combat. Otherwise, you draw a card and Gollum can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(new GollumSchemingGuideEffect()));
    }

    private GollumSchemingGuide(final GollumSchemingGuide card) {
        super(card);
    }

    @Override
    public GollumSchemingGuide copy() {
        return new GollumSchemingGuide(this);
    }
}

class GollumSchemingGuideEffect extends OneShotEffect {

    GollumSchemingGuideEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top two cards of your library, " +
            "put them back in any order, then choose land or nonland. " +
            "An opponent guesses whether the top card of your library " +
            "is the chosen kind. Reveal that card. If they guessed right, " +
            "remove {this} from combat. Otherwise, you draw a card and {this} " +
            "can't be blocked this turn";
    }

    private GollumSchemingGuideEffect(final GollumSchemingGuideEffect effect) {
        super(effect);
    }

    @Override
    public GollumSchemingGuideEffect copy() {
        return new GollumSchemingGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        // look at the top two cards of your library,
        player.lookAtCards(source, null, cards, game);
        // put them back in any order
        PutCards.TOP_ANY.moveCards(player, cards, source, game);

        // then choose land or nonland.
        boolean guessLand = player.chooseUse(Outcome.Neutral,
            "Choose land or nonland (opponent must then guess)",
            "", "land", "nonland", source, game);

        // choose an opponent.
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (!player.choose(Outcome.Neutral, targetOpponent, source, game)) {
            return false;
        }

        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }

        String textGuess = guessLand ? "land" : "nonland";

        FilterCard filter = guessLand
            ? StaticFilters.FILTER_CARD_LAND
            : StaticFilters.FILTER_CARD_NON_LAND;

        // An opponent guesses whether the top card of your library is the chosen kind.
        boolean guessOpp = opponent.chooseUse(Outcome.Neutral,
            "Is the top card of " + player.getLogName() + "'s library a " + textGuess + " card?", source, game);

        // End of the choices, last check on whether the player still exist.
        Card card = player.getLibrary().getFromTop(game);
        player.revealCards(source, new CardsImpl(card), game);

        if (card != null && (filter.match(card, player.getId(), source, game) == guessOpp)) {
            game.informPlayers(opponent.getLogName() + " guessed right.");

            // remove Gollum from combat.
            Permanent gollum = source.getSourcePermanentIfItStillExists(game);
            if (gollum != null) {
                gollum.removeFromCombat(game);
            }
        } else {
            game.informPlayers(opponent.getLogName() + " guessed wrong.");

            // you draw a card
            player.drawCards(1, source, game);

            // and Gollum can't be blocked this turn
            Permanent gollum = source.getSourcePermanentIfItStillExists(game);
            if (gollum != null) {
                game.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn), source);
            }
        }

        return true;
    }
}