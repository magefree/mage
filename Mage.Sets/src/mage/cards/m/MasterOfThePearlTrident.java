
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class MasterOfThePearlTrident extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk creatures");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public MasterOfThePearlTrident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Merfolk creatures you control get +1/+1 and have islandwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new IslandwalkAbility(), Duration.WhileOnBattlefield, filter, true)));
    }

    public MasterOfThePearlTrident(final MasterOfThePearlTrident card) {
        super(card);
    }

    @Override
    public MasterOfThePearlTrident copy() {
        return new MasterOfThePearlTrident(this);
    }
}
