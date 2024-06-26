

package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class ClifftopRetreat extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain or a Plains");

    static {
        filter.add(Predicates.or(SubType.MOUNTAIN.getPredicate(), SubType.PLAINS.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public ClifftopRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Clifftop Retreat enters the battlefield tapped unless you control a Mountain or a Plains.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private ClifftopRetreat(final ClifftopRetreat card) {
        super(card);
    }

    @Override
    public ClifftopRetreat copy() {
        return new ClifftopRetreat(this);
    }
}
