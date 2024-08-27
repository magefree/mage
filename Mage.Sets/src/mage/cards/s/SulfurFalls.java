

package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class SulfurFalls extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("an Island or a Mountain");

    static {
        filter.add(Predicates.or(SubType.ISLAND.getPredicate(), SubType.MOUNTAIN.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public SulfurFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Sulfur Falls enters the battlefield tapped unless you control an Island or a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private SulfurFalls(final SulfurFalls card) {
        super(card);
    }

    @Override
    public SulfurFalls copy() {
        return new SulfurFalls(this);
    }
}
