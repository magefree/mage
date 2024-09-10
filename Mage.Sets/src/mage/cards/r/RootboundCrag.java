
package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class RootboundCrag extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain or a Forest");

    static {
        filter.add(Predicates.or(SubType.MOUNTAIN.getPredicate(), SubType.FOREST.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public RootboundCrag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Rootbound Crag enters the battlefield tapped unless you control a Mountain or a Forest.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private RootboundCrag(final RootboundCrag card) {
        super(card);
    }

    @Override
    public RootboundCrag copy() {
        return new RootboundCrag(this);
    }
}
