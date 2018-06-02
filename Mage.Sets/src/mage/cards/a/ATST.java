
package mage.cards.a;

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
 * @author Styxo
 */
public final class ATST extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures");

    static {
        filter.add(new SubtypePredicate(SubType.TROOPER));
    }

    public ATST(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.TROOPER);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trooper creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield, filter, false)));

    }

    public ATST(final ATST card) {
        super(card);
    }

    @Override
    public ATST copy() {
        return new ATST(this);
    }
}
