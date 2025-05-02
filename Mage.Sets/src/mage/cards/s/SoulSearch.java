package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulSearch extends CardImpl {

    public SoulSearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. Exile that card. If the card's mana value is 1 or less, create a 1/1 white and black Spirit creature token with flying.
        this.getSpellAbility().addEffect(new SoulSearchEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SoulSearch(final SoulSearch card) {
        super(card);
    }

    @Override
    public SoulSearch copy() {
        return new SoulSearch(this);
    }
}

class SoulSearchEffect extends OneShotEffect {

    SoulSearchEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You choose a nonland card from it. Exile that card. " +
                "If the card's mana value is 1 or less, create a 1/1 white and black Spirit creature token with flying";
    }

    private SoulSearchEffect(final SoulSearchEffect effect) {
        super(effect);
    }

    @Override
    public SoulSearchEffect copy() {
        return new SoulSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        if (opponent.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInHand(StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(Outcome.Discard, opponent.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        if (card.getManaValue() <= 1) {
            new WhiteBlackSpiritToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
