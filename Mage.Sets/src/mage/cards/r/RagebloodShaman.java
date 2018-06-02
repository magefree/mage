
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public final class RagebloodShaman extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.MINOTAUR, "Minotaur creatures");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public RagebloodShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Other Minotaur creatures you control get +1/+1 and have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(new StaticValue(1), new StaticValue(1), Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
    }

    public RagebloodShaman(final RagebloodShaman card) {
        super(card);
    }

    @Override
    public RagebloodShaman copy() {
        return new RagebloodShaman(this);
    }
}
