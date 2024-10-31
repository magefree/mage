package mage.cards.g;

import java.util.Collection;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author Jamaninja
 */
public final class GrabThePrize extends CardImpl {

    public GrabThePrize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // If the discarded card wasn't a land card, {this} deals two damage to each opponent.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DamagePlayersEffect(2, TargetController.OPPONENT),
            GrabThePrizeCondition.instance,
        "If the discarded card wasn't a land card, {this} deals two damage to each opponent."));
    }

    private GrabThePrize(final GrabThePrize card) {
        super(card);
    }

    @Override
    public GrabThePrize copy() {
        return new GrabThePrize(this);
    }
}

enum GrabThePrizeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.castStream(source.getCosts().stream(), DiscardCardCost.class)
                .map(DiscardCardCost::getCards)
                .flatMap(Collection::stream)
                .anyMatch(card -> !card.isLand(game));
    }
}