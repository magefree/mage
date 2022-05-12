package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.DashAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public enum DashedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        return card != null
                && CardUtil.castStream(card
                .getAbilities(game)
                .stream(), DashAbility.class)
                .anyMatch(ability -> ability.isActivated(source, game));
    }
}
