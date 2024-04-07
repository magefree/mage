package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MakeYourOwnLuck extends CardImpl {

    public MakeYourOwnLuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");

        // Look at the top three cards of your library. You may exile a nonland card from among them. If you do, it becomes plotted. Put the rest into your hand.
        this.getSpellAbility().addEffect(new MakeYourOwnLuckEffect());
    }

    private MakeYourOwnLuck(final MakeYourOwnLuck card) {
        super(card);
    }

    @Override
    public MakeYourOwnLuck copy() {
        return new MakeYourOwnLuck(this);
    }
}

class MakeYourOwnLuckEffect extends LookLibraryAndPickControllerEffect {

    MakeYourOwnLuckEffect() {
        super(3, 1, StaticFilters.FILTER_CARD_NON_LAND, PutCards.EXILED, PutCards.HAND);
        staticText = "Look at the top three cards of your library. "
                + "You may exile a nonland card from among them. "
                + "If you do, it becomes plotted. Put the rest into your hand";
    }

    private MakeYourOwnLuckEffect(final MakeYourOwnLuckEffect effect) {
        super(effect);
    }

    @Override
    public MakeYourOwnLuckEffect copy() {
        return new MakeYourOwnLuckEffect(this);
    }

    @Override
    public boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        boolean result = false;
        for (Card card : pickedCards.getCards(game)) {
            result |= PlotAbility.doExileAndPlotCard(card, game, source);
        }
        result |= putLookedCards.moveCards(player, otherCards, source, game);
        return result;
    }
}
