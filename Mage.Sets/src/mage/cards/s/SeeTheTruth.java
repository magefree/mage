package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeeTheTruth extends CardImpl {

    public SeeTheTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top three cards of your library. Put one of those cards into your hand and the rest on the bottom of your library in any order. If this spell was cast from anywhere other than your hand, put each of those cards into your hand instead.
        this.getSpellAbility().addEffect(new SeeTheTruthEffect());
        this.getSpellAbility().addWatcher(new CastFromHandWatcher());
    }

    private SeeTheTruth(final SeeTheTruth card) {
        super(card);
    }

    @Override
    public SeeTheTruth copy() {
        return new SeeTheTruth(this);
    }
}

class SeeTheTruthEffect extends OneShotEffect {

    SeeTheTruthEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top three cards of your library. " +
                "Put one of those cards into your hand and the rest on the bottom of your library in any order. " +
                "If this spell was cast from anywhere other than your hand, " +
                "put each of those cards into your hand instead.";
    }

    private SeeTheTruthEffect(final SeeTheTruthEffect effect) {
        super(effect);
    }

    @Override
    public SeeTheTruthEffect copy() {
        return new SeeTheTruthEffect(this);
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
        if (CastFromHandSourceCondition.instance.apply(game, source)) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            player.choose(outcome, cards, target, game);
            cards.removeIf(target.getFirstTarget()::equals);
            player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
            player.putCardsOnBottomOfLibrary(cards, game, source, true);
        } else {
            player.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }
}
