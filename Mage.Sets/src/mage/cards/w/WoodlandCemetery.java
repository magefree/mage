
package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class WoodlandCemetery extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Swamp or a Forest");

    static {
        filter.add(Predicates.or(SubType.SWAMP.getPredicate(), SubType.FOREST.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public WoodlandCemetery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Woodland Cemetery enters the battlefield tapped unless you control a Swamp or a Forest.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private WoodlandCemetery(final WoodlandCemetery card) {
        super(card);
    }

    @Override
    public WoodlandCemetery copy() {
        return new WoodlandCemetery(this);
    }
}
