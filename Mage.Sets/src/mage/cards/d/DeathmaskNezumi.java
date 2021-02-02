
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DeathmaskNezumi extends CardImpl {

    public DeathmaskNezumi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you have seven or more cards in hand, Deathmask Nezumi gets +2/+1 and has fear.
        Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN,6);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2,1, Duration.WhileOnBattlefield), condition, "As long as you have seven or more cards in hand, {this} gets +2/+1"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FearAbility.getInstance(), Duration.WhileOnBattlefield), condition, "and has fear"));
        this.addAbility(ability);
    }

    private DeathmaskNezumi(final DeathmaskNezumi card) {
        super(card);
    }

    @Override
    public DeathmaskNezumi copy() {
        return new DeathmaskNezumi(this);
    }
}
