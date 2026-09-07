package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class WestCoastExpansion extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.HERO, "a Hero spell");

    public WestCoastExpansion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Draw X cards. If X is 5 or more, you may cast a Hero spell from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GetXValue.instance));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new CastFromHandForFreeEffect(filter),
            WestCoastExpansionCondition.instance,
            "If X is 5 or more, you may cast a Hero spell from your hand without paying its mana cost."
        ));
    }

    private WestCoastExpansion(final WestCoastExpansion card) {
        super(card);
    }

    @Override
    public WestCoastExpansion copy() {
        return new WestCoastExpansion(this);
    }
}

enum WestCoastExpansionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostsTag(game, source, "X", 0) >= 5;
    }
}
