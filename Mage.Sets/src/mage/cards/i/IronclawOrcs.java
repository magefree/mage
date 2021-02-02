
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author dustinconrad
 */
public final class IronclawOrcs extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public IronclawOrcs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ironclaw Orcs can't block creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));
    }

    private IronclawOrcs(final IronclawOrcs card) {
        super(card);
    }

    @Override
    public IronclawOrcs copy() {
        return new IronclawOrcs(this);
    }
}
