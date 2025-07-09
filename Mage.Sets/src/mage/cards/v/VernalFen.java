package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class VernalFen extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public VernalFen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {B} or {G}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // This land enters tapped unless you control two or more basic lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));
    }

    private VernalFen(final VernalFen card) {
        super(card);
    }

    @Override
    public VernalFen copy() {
        return new VernalFen(this);
    }
}
