
package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
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
public final class HinterlandHarbor extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Forest or an Island");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(), SubType.ISLAND.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public HinterlandHarbor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Hinterland Harbor enters the battlefield tapped unless you control a Forest or an Island.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private HinterlandHarbor(final HinterlandHarbor card) {
        super(card);
    }

    @Override
    public HinterlandHarbor copy() {
        return new HinterlandHarbor(this);
    }
}
