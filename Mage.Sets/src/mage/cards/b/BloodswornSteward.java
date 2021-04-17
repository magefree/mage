
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

/**
 *
 * @author Saga
 */
public final class BloodswornSteward extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Commander creatures");
    static {
        filter.add(CommanderPredicate.instance);
    }
    
    public BloodswornSteward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Commander creatures you control get +2/+2 and have haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have haste");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BloodswornSteward(final BloodswornSteward card) {
        super(card);
    }

    @Override
    public BloodswornSteward copy() {
        return new BloodswornSteward(this);
    }
}
