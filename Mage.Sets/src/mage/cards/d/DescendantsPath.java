package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public final class DescendantsPath extends CardImpl {

    public DescendantsPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, reveal the top card of your library. 
        // If it's a creature card that shares a creature type with a creature you control, 
        // you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DescendantsPathEffect(), TargetController.YOU, false));
    }

    private DescendantsPath(final DescendantsPath card) {
        super(card);
    }

    @Override
    public DescendantsPath copy() {
        return new DescendantsPath(this);
    }
}

class DescendantsPathEffect extends OneShotEffect {

    public DescendantsPathEffect() {
        super(Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a creature "
                + "card that shares a creature type with a creature you control, "
                + "you may cast it without paying its mana cost. If you don't cast it, " +
                "put it on the bottom of your library";
    }

    public DescendantsPathEffect(final DescendantsPathEffect effect) {
        super(effect);
    }

    @Override
    public DescendantsPathEffect copy() {
        return new DescendantsPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        if (card.isCreature(game)
                && game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source.getControllerId(), game
                ).stream()
                .anyMatch(permanent -> permanent.shareCreatureTypes(game, card))) {
            CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
        }
        if (game.getState().getZone(card.getId()) == Zone.LIBRARY) {
            controller.putCardsOnBottomOfLibrary(card, game, source, false);
        }
        return true;
    }
}
