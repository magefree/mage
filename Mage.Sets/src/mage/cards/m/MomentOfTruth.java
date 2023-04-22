package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

public class MomentOfTruth extends CardImpl {
    public MomentOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        //Look at the top three cards of your library. Put one of those cards into your hand, one into your graveyard,
        //and one on the bottom of your library.
        this.getSpellAbility().addEffect(new MomentOfTruthEffect());
    }

    private MomentOfTruth(final MomentOfTruth card) {
        super(card);
    }

    @Override
    public MomentOfTruth copy() {
        return new MomentOfTruth(this);
    }
}

class MomentOfTruthEffect extends OneShotEffect {

    MomentOfTruthEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top three cards of your library. Put one of those cards into your hand, one into " +
                "your graveyard, and one on the bottom of your library";
    }

    private MomentOfTruthEffect(final MomentOfTruthEffect effect) {
        super(effect);
    }

    @Override
    public MomentOfTruthEffect copy() {
        return new MomentOfTruthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (cards.size() < 1) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(
                cards.size() == 3 ? 1 : 0, 1, StaticFilters.FILTER_CARD
        );
        target.withChooseHint("To put into your hand");
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
        }
        if (cards.isEmpty()) {
            return true;
        }
        target = new TargetCardInLibrary(
                cards.size() == 2 ? 1 : 0, 1, StaticFilters.FILTER_CARD
        );
        target.withChooseHint("To put into your graveyard");
        player.choose(outcome, cards, target, source, game);
        card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.putInGraveyard(card, game);
            cards.remove(card);
        }
        if (cards.isEmpty()) {
            return true;
        }
        target = new TargetCardInLibrary();
        target.withChooseHint("To put on the bottom of your library");
        player.choose(outcome, cards, target, source, game);
        card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        player.putCardsOnBottomOfLibrary(card, game, source, false);
        return true;
    }
}
