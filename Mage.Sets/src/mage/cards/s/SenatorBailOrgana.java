
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SenatorBailOrgana extends CardImpl {

    public SenatorBailOrgana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you control four or more creatures, creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(), ComparisonType.MORE_THAN, 3),
                "As long as you control four or more creatures, creatures you control get +1/+1")
        ));

    }

    private SenatorBailOrgana(final SenatorBailOrgana card) {
        super(card);
    }

    @Override
    public SenatorBailOrgana copy() {
        return new SenatorBailOrgana(this);
    }
}
