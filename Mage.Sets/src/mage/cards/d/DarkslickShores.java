
package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class DarkslickShores extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public DarkslickShores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Darkslick Shores enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private DarkslickShores(final DarkslickShores card) {
        super(card);
    }

    @Override
    public DarkslickShores copy() {
        return new DarkslickShores(this);
    }

}
