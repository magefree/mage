
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class RakshaGoldenCub extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cat creatures you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.CAT.getPredicate());
    }
    
    public RakshaGoldenCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // As long as Raksha Golden Cub is equipped, Cat creatures you control get +2/+2 and have double strike.
        Effect effect1 = new ConditionalContinuousEffect(
                new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
                EquippedSourceCondition.instance,
                "As long as {this} is equipped, Cat creatures you control get +2/+2");
        
        Effect effect2 = new ConditionalContinuousEffect(
                new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, false),
                EquippedSourceCondition.instance,
                "As long as {this} is equipped, Cat creatures you control have double strike");
        effect2.setText("and have double strike");
        
        
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        
        this.addAbility(ability);
    }

    private RakshaGoldenCub(final RakshaGoldenCub card) {
        super(card);
    }

    @Override
    public RakshaGoldenCub copy() {
        return new RakshaGoldenCub(this);
    }
}
