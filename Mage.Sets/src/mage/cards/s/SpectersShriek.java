package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectersShriek extends CardImpl {

    public SpectersShriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player exiles that card. If a nonblack card is exiled this way, exile a card from your hand.
        this.getSpellAbility().addEffect(new SpectersShriekEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SpectersShriek(final SpectersShriek card) {
        super(card);
    }

    @Override
    public SpectersShriek copy() {
        return new SpectersShriek(this);
    }
}

class SpectersShriekEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card in your hand (to exile)");

    SpectersShriekEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. You may choose a nonland card from it. If you do, " +
                "that player exiles that card. If a nonblack card is exiled this way, exile a card from your hand.";
    }

    private SpectersShriekEffect(final SpectersShriekEffect effect) {
        super(effect);
    }

    @Override
    public SpectersShriekEffect copy() {
        return new SpectersShriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        if (player.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) == 0
                || !controller.chooseUse(outcome, "Exile a card from " + player.getName() + "'s hand?", source, game)) {
            return true;
        }
        TargetCard target = new TargetCardInHand(StaticFilters.FILTER_CARD_NON_LAND);
        target.setNotTarget(true);
        if (!controller.choose(outcome, player.getHand(), target, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        boolean isBlack = card.getColor(game).isBlack();
        player.moveCards(card, Zone.EXILED, source, game);
        if (isBlack || controller.getHand().isEmpty()) {
            return true;
        }
        target = new TargetCardInHand(filter);
        target.setNotTarget(true);
        return controller.choose(outcome, controller.getHand(), target, game)
                && controller.moveCards(game.getCard(target.getFirstTarget()), Zone.EXILED, source, game);
    }
}