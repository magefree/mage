
package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BloomingMarsh extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public BloomingMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Blooming Marsh enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private BloomingMarsh(final BloomingMarsh card) {
        super(card);
    }

    @Override
    public BloomingMarsh copy() {
        return new BloomingMarsh(this);
    }
}
