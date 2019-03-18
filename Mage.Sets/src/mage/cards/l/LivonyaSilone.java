
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author LoneFox
 */
public final class LivonyaSilone extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Legendary land");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public LivonyaSilone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // legendary landwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    public LivonyaSilone(final LivonyaSilone card) {
        super(card);
    }

    @Override
    public LivonyaSilone copy() {
        return new LivonyaSilone(this);
    }
}
