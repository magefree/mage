

package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
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
public final class IsolatedChapel extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Plains or a Swamp");

    static {
        filter.add(Predicates.or(SubType.PLAINS.getPredicate(), SubType.SWAMP.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public IsolatedChapel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Isolated Chapel enters the battlefield tapped unless you control a Plains or a Swamp.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private IsolatedChapel(final IsolatedChapel card) {
        super(card);
    }

    @Override
    public IsolatedChapel copy() {
        return new IsolatedChapel(this);
    }
}
