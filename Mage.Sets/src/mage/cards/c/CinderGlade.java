
package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CinderGlade extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public CinderGlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // Cinder Glade enters the battlefield tapped unless you control two or more basic lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private CinderGlade(final CinderGlade card) {
        super(card);
    }

    @Override
    public CinderGlade copy() {
        return new CinderGlade(this);
    }
}
