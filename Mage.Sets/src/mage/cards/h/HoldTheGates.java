
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class HoldTheGates extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public HoldTheGates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // Creatures you control get +0/+1 for each Gate you control and have vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(new StaticValue(0),new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"))
                .setText("Creatures you control get +0/+1 for each Gate you control and have vigilance"));
        this.addAbility(ability);
    }

    public HoldTheGates(final HoldTheGates card) {
        super(card);
    }

    @Override
    public HoldTheGates copy() {
        return new HoldTheGates(this);
    }
}
