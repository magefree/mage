
package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class SpirebluffCanal extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_LESS, 2);

    public SpirebluffCanal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Spirebluff Canal enters the battlefield tapped unless you control two or fewer other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private SpirebluffCanal(final SpirebluffCanal card) {
        super(card);
    }

    @Override
    public SpirebluffCanal copy() {
        return new SpirebluffCanal(this);
    }
}
