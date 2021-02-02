
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class AkkiUnderling extends CardImpl {

    public AkkiUnderling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as you have seven or more cards in hand, Akki Underling gets +2/+1 and has first strike.
        Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN,6);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2,1, Duration.WhileOnBattlefield), condition, "As long as you have seven or more cards in hand, {this} gets +2/+1"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield), condition, "and has first strike"));
        this.addAbility(ability);
    }

    private AkkiUnderling(final AkkiUnderling card) {
        super(card);
    }

    @Override
    public AkkiUnderling copy() {
        return new AkkiUnderling(this);
    }
}
