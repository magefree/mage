package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public final class BlossomingWreath extends CardImpl {

    public BlossomingWreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // You gain life equal to the number of creature cards in your graveyard.
        this.getSpellAbility().addEffect(new BlossomingWreathEffect());
    }

    private BlossomingWreath(final BlossomingWreath card) {
        super(card);
    }

    @Override
    public BlossomingWreath copy() {
        return new BlossomingWreath(this);
    }

    static class BlossomingWreathEffect extends OneShotEffect {

        public BlossomingWreathEffect() {
            super(Outcome.GainLife);
            this.staticText = "You gain life equal to the number of creature cards in your graveyard";
        }

        private BlossomingWreathEffect(final BlossomingWreathEffect effect) {
            super(effect);
        }

        @Override
        public BlossomingWreathEffect copy() {
            return new BlossomingWreathEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(controller.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game), game, source);
                return true;
            }
            return false;
        }
    }
}
