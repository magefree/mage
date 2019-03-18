
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
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
 * @author Loki
 */
public final class WizenedCenn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kithkin creatures");

    static {
        filter.add(new SubtypePredicate(SubType.KITHKIN));
    }

    public WizenedCenn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Other Kithkin creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public WizenedCenn(final WizenedCenn card) {
        super(card);
    }

    @Override
    public WizenedCenn copy() {
        return new WizenedCenn(this);
    }
}
