
package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
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
public final class DragonskullSummit extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Swamp or a Mountain");

    static {
        filter.add(Predicates.or(SubType.SWAMP.getPredicate(), SubType.MOUNTAIN.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public DragonskullSummit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Dragonskull Summit enters the battlefield tapped unless you control a Swamp or a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private DragonskullSummit(final DragonskullSummit card) {
        super(card);
    }

    @Override
    public DragonskullSummit copy() {
        return new DragonskullSummit(this);
    }
}
