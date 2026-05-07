package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class DinasGuidance extends CardImpl {

    public DinasGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{G}");

        // Search your library for a creature card, reveal it, put it into your hand or graveyard, then shuffle.
        this.getSpellAbility().addEffect(new DinasGuidanceEffect());
    }

    private DinasGuidance(final DinasGuidance card) {
        super(card);
    }

    @Override
    public DinasGuidance copy() {
        return new DinasGuidance(this);
    }
}

class DinasGuidanceEffect extends OneShotEffect {

    DinasGuidanceEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a creature card, reveal it, put it into your hand or graveyard, then shuffle";
    }

    private DinasGuidanceEffect(final DinasGuidanceEffect effect) {
        super(effect);
    }

    @Override
    public DinasGuidanceEffect copy() {
        return new DinasGuidanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE);
        if (controller.searchLibrary(target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.revealCards(source, new CardsImpl(card), game);
                if (controller.chooseUse(Outcome.Benefit, "Put " + card.getName() + " into your hand or graveyard?",
                        null, "Hand", "Graveyard", source, game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
