
package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class LostInTheWoods extends CardImpl {

    public LostInTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // Whenever a creature attacks you or a planeswalker you control, reveal the top card of your library. If it's a Forest card, remove that creature from combat. Then put the revealed card on the bottom of your library.
        this.addAbility(new AttacksAllTriggeredAbility(new LostInTheWoodsEffect(), false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PERMANENT, true));
    }

    private LostInTheWoods(final LostInTheWoods card) {
        super(card);
    }

    @Override
    public LostInTheWoods copy() {
        return new LostInTheWoods(this);
    }
}

class LostInTheWoodsEffect extends OneShotEffect {

    public LostInTheWoodsEffect() {
        super(Outcome.PreventDamage);
        staticText = "reveal the top card of your library. If it's a Forest card, remove that creature from combat. Then put the revealed card on the bottom of your library";
    }

    public LostInTheWoodsEffect(final LostInTheWoodsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null || controller == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getName(), cards, game);

            if (card != null) {
                if (card.hasSubtype(SubType.FOREST, game)) {
                    Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
                    if (permanent != null) {
                        permanent.removeFromCombat(game);
                    }
                }
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, true);
            }
        }
        return true;
    }

    @Override
    public LostInTheWoodsEffect copy() {
        return new LostInTheWoodsEffect(this);
    }

}
