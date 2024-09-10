package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpressiveIteration extends CardImpl {

    public ExpressiveIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{R}");

        // Look at the top three cards of your library. Put one of them into your hand, put one of them on the bottom of your library, and exile one of them. You may play the exiled card this turn.
        this.getSpellAbility().addEffect(new ExpressiveIterationEffect());
    }

    private ExpressiveIteration(final ExpressiveIteration card) {
        super(card);
    }

    @Override
    public ExpressiveIteration copy() {
        return new ExpressiveIteration(this);
    }
}

class ExpressiveIterationEffect extends OneShotEffect {

    ExpressiveIterationEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top three cards of your library. Put one of them into your hand, put one of them " +
                "on the bottom of your library, and exile one of them. You may play the exiled card this turn";
    }

    private ExpressiveIterationEffect(final ExpressiveIterationEffect effect) {
        super(effect);
    }

    @Override
    public ExpressiveIterationEffect copy() {
        return new ExpressiveIterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null || sourceObject == null) {
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
        target.withChooseHint("To put on the bottom of your library");
        player.choose(outcome, cards, target, source, game);
        card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.putCardsOnBottomOfLibrary(card, game, source, false);
            cards.remove(card);
        }
        if (cards.isEmpty()) {
            return true;
        }
        target = new TargetCardInLibrary();
        target.withChooseHint("To exile (you may play it this turn)");
        player.choose(outcome, cards, target, source, game);
        card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        player.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
