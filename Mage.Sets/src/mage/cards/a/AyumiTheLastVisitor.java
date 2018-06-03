
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
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
 * @author LevelX2
 */
public final class AyumiTheLastVisitor extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Legendary land");
    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public AyumiTheLastVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // Legendary landwalk
        this.addAbility(new LandwalkAbility(filter));

    }

    public AyumiTheLastVisitor(final AyumiTheLastVisitor card) {
        super(card);
    }

    @Override
    public AyumiTheLastVisitor copy() {
        return new AyumiTheLastVisitor(this);
    }
}
