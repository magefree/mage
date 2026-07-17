package mage.cards.e;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EclipsedSteppe extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public EclipsedSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W} or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // This land enters tapped unless you control two or more basic lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));
    }

    private EclipsedSteppe(final EclipsedSteppe card) {
        super(card);
    }

    @Override
    public EclipsedSteppe copy() {
        return new EclipsedSteppe(this);
    }
}
