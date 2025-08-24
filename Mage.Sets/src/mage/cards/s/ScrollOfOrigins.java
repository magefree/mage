package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ScrollOfOrigins extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 6);

    public ScrollOfOrigins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {tap}: Draw a card if you have seven or more cards in hand.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "draw a card if you have seven or more cards in hand"
        ), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ScrollOfOrigins(final ScrollOfOrigins card) {
        super(card);
    }

    @Override
    public ScrollOfOrigins copy() {
        return new ScrollOfOrigins(this);
    }
}
