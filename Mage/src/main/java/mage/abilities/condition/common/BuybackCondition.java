package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author spjspj
 */
public enum BuybackCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return card.getAbilities(game).stream()
                    .filter(BuybackAbility.class::isInstance)
                    .anyMatch(a -> ((BuybackAbility) a).isBuybackActivated(game));
        }
        return false;
    }
}
