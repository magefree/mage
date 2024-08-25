package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CruelclawsHeist extends CardImpl {

    public CruelclawsHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Target opponent reveals their hand. You choose a nonland card from it. Exile that card. If the gift was promised, you may cast that card for as long as it remains exiled, and mana of any type can be spent to cast it.
        this.getSpellAbility().addEffect(new CruelclawsHeistEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CruelclawsHeist(final CruelclawsHeist card) {
        super(card);
    }

    @Override
    public CruelclawsHeist copy() {
        return new CruelclawsHeist(this);
    }
}

class CruelclawsHeistEffect extends OneShotEffect {

    CruelclawsHeistEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You choose a nonland card from it. " +
                "Exile that card. If the gift was promised, you may cast that card " +
                "for as long as it remains exiled, and mana of any type can be spent to cast it";
    }

    private CruelclawsHeistEffect(final CruelclawsHeistEffect effect) {
        super(effect);
    }

    @Override
    public CruelclawsHeistEffect copy() {
        return new CruelclawsHeistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getHand());
        opponent.revealCards(source, cards, game);
        if (cards.count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInHand(StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(Outcome.Discard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        if (GiftWasPromisedCondition.TRUE.apply(game, source)) {
            CardUtil.makeCardPlayable(game, source, card, true, Duration.Custom, true);
        }
        return true;
    }
}
