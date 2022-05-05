package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AdventurePredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoryTheft extends CardImpl {

    public MemoryTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card. You may put a card that has an Adventure that player owns from exile into that player's graveyard.
        this.getSpellAbility().addEffect(new MemoryTheftEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private MemoryTheft(final MemoryTheft card) {
        super(card);
    }

    @Override
    public MemoryTheft copy() {
        return new MemoryTheft(this);
    }
}

class MemoryTheftEffect extends OneShotEffect {

    MemoryTheftEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. You choose a nonland card from it. " +
                "That player discards that card. You may put a card that has an Adventure " +
                "that player owns from exile into that player's graveyard.";
    }

    private MemoryTheftEffect(final MemoryTheftEffect effect) {
        super(effect);
    }

    @Override
    public MemoryTheftEffect copy() {
        return new MemoryTheftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY
        ).apply(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card owned by " + player.getName() + " that has an Adventure");
        filter.add(AdventurePredicate.instance);
        filter.add(new OwnerIdPredicate(player.getId()));
        TargetCard target = new TargetCardInExile(0, 1, filter, null, true);
        if (!target.canChoose(source.getControllerId(), source, game)
                || !controller.choose(outcome, target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        return controller.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}