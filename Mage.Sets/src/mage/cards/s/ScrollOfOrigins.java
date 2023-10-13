package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ScrollOfOrigins extends CardImpl {

    public ScrollOfOrigins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, {tap}: Draw a card if you have seven or more cards in hand.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2),
                new CardsInHandCondition(ComparisonType.MORE_THAN, 6),
                "{2}, {T}: Draw a card if you have seven or more cards in hand.");
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
