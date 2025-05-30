package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801 plus everyone who worked on Gonti
 */
public final class SiphonInsight extends CardImpl {

    public SiphonInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Look at the top two cards of target opponent's library. Exile one of them face down and put the other on the bottom of that library. You may look at and play the exiled card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addEffect(new SiphonInsightEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Flashback {1}{U}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}{B}")));
    }

    private SiphonInsight(final SiphonInsight card) {
        super(card);
    }

    @Override
    public SiphonInsight copy() {
        return new SiphonInsight(this);
    }
}

class SiphonInsightEffect extends OneShotEffect {

    private static final String VALUE_PREFIX = "ExileZones";

    public SiphonInsightEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top two cards of target opponent's library. " +
                "Exile one of them face down and put the other on the bottom of that library. " +
                "You may look at and play the exiled card for as long as it remains exiled, " +
                "and you may spend mana as though it were mana of any color to cast that spell";
    }

    private SiphonInsightEffect(final SiphonInsightEffect effect) {
        super(effect);
    }

    @Override
    public SiphonInsightEffect copy() {
        return new SiphonInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || opponent == null || sourceObject == null) {
            return false;
        }
        Cards topCards = new CardsImpl();
        topCards.addAllCards(opponent.getLibrary().getTopCards(game, 2));
        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
        controller.choose(outcome, topCards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
            return true;
        }
        new ExileFaceDownYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
        topCards.retainZone(Zone.LIBRARY, game);
        // then put the rest on the bottom of that library in a random order
        controller.putCardsOnBottomOfLibrary(topCards, game, source, false);
        return true;
    }
}