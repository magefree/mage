
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author fireshoes
 */
public final class ZurgoBellstriker extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or greater");
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public ZurgoBellstriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Zurgo Bellstriker can't block creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));
        // Dash {1}{R}
        this.addAbility(new DashAbility("{1}{R}"));
    }

    private ZurgoBellstriker(final ZurgoBellstriker card) {
        super(card);
    }

    @Override
    public ZurgoBellstriker copy() {
        return new ZurgoBellstriker(this);
    }
}
