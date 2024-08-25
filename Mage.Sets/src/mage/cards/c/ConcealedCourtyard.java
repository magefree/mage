
package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class ConcealedCourtyard extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public ConcealedCourtyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Concealed Courtyard enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.

        this.addAbility(new WhiteManaAbility());
        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private ConcealedCourtyard(final ConcealedCourtyard card) {
        super(card);
    }

    @Override
    public ConcealedCourtyard copy() {
        return new ConcealedCourtyard(this);
    }
}
