
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author fireshoes
 */
public final class LegionsOfLimDul extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("snow swamp");
    
    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
        filter.add(new SubtypePredicate(SubType.SWAMP   ));
    }

    public LegionsOfLimDul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Snow swampwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    public LegionsOfLimDul(final LegionsOfLimDul card) {
        super(card);
    }

    @Override
    public LegionsOfLimDul copy() {
        return new LegionsOfLimDul(this);
    }
}
