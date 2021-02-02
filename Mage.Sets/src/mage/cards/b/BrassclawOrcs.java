
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author dustinconrad
 */
public final class BrassclawOrcs extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public BrassclawOrcs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Brassclaw Orcs can't block creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));

    }

    private BrassclawOrcs(final BrassclawOrcs card) {
        super(card);
    }

    @Override
    public BrassclawOrcs copy() {
        return new BrassclawOrcs(this);
    }
}
