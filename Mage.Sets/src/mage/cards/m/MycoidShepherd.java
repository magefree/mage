
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MycoidShepherd extends CardImpl {
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control with power 5 or greater");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public MycoidShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}{W}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Mycoid Shepherd or another creature you control with power 5 or greater dies, you may gain 5 life.
        this.addAbility(new DiesThisOrAnotherTriggeredAbility(new GainLifeEffect(5), true, filter));

    }

    private MycoidShepherd(final MycoidShepherd card) {
        super(card);
    }

    @Override
    public MycoidShepherd copy() {
        return new MycoidShepherd(this);
    }
}
