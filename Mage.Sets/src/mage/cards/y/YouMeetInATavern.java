package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouMeetInATavern extends CardImpl {

    public YouMeetInATavern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Choose one —
        // • Form a Party — Look at the top five cards of your library. You may reveal any number of creature cards from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new YouMeetInATavernEffect());
        this.getSpellAbility().withFirstModeFlavorWord("Form a Party");

        // • Start a Brawl — Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        )).withFlavorWord("Start a Brawl"));
    }

    private YouMeetInATavern(final YouMeetInATavern card) {
        super(card);
    }

    @Override
    public YouMeetInATavern copy() {
        return new YouMeetInATavern(this);
    }
}

class YouMeetInATavernEffect extends OneShotEffect {

    YouMeetInATavernEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top five cards of your library. You may reveal " +
                "any number of creature cards from among them and put them into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private YouMeetInATavernEffect(final YouMeetInATavernEffect effect) {
        super(effect);
    }

    @Override
    public YouMeetInATavernEffect copy() {
        return new YouMeetInATavernEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, target, game);
        Cards toHand = new CardsImpl(target.getTargets());
        cards.removeAll(toHand);
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
