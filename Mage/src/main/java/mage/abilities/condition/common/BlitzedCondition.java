package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum BlitzedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        return card != null
                && CardUtil.castStream(card
                .getAbilities(game)
                .stream(), BlitzAbility.class)
                .anyMatch(ability -> ability.isActivated(source, game));
    }
}
