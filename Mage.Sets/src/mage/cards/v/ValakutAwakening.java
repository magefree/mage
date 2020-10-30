package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValakutAwakening extends CardImpl {

    public ValakutAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.v.ValakutStoneforge.class;

        // Put any number of cards from your hand on the bottom of your library, then draw that many cards plus one.
        this.getSpellAbility().addEffect(new ValakutAwakeningEffect());
    }

    private ValakutAwakening(final ValakutAwakening card) {
        super(card);
    }

    @Override
    public ValakutAwakening copy() {
        return new ValakutAwakening(this);
    }
}

class ValakutAwakeningEffect extends OneShotEffect {

    ValakutAwakeningEffect() {
        super(Outcome.Benefit);
        staticText = "put any number of cards from your hand on the bottom of your library, " +
                "then draw that many cards plus one";
    }

    private ValakutAwakeningEffect(final ValakutAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public ValakutAwakeningEffect copy() {
        return new ValakutAwakeningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInHand(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CARDS
        );
        player.choose(outcome, player.getHand(), targetCard, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        player.drawCards(cards.size() + 1, source.getSourceId(), game);
        return true;
    }
}
