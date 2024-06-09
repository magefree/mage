package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarbeastOfGorgoroth extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public WarbeastOfGorgoroth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Warbeast of Gorgoroth or another creature you control with power 4 or greater dies, amass Orcs 2.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new AmassEffect(2, SubType.ORC), false, filter
        ));
    }

    private WarbeastOfGorgoroth(final WarbeastOfGorgoroth card) {
        super(card);
    }

    @Override
    public WarbeastOfGorgoroth copy() {
        return new WarbeastOfGorgoroth(this);
    }
}
