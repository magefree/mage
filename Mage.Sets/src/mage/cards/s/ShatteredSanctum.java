package mage.cards.s;

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
 * @author TheElk801
 */
public final class ShatteredSanctum extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public ShatteredSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Shattered Sanctum enters the battlefield tapped unless you control two or more other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private ShatteredSanctum(final ShatteredSanctum card) {
        super(card);
    }

    @Override
    public ShatteredSanctum copy() {
        return new ShatteredSanctum(this);
    }
}
