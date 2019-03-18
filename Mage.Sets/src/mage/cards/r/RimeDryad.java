
package mage.cards.r;

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
public final class RimeDryad extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow forest");

    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public RimeDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Snow forestwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    public RimeDryad(final RimeDryad card) {
        super(card);
    }

    @Override
    public RimeDryad copy() {
        return new RimeDryad(this);
    }
}
