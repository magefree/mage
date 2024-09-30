
package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class SunpetalGrove extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Forest or a Plains");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(), SubType.PLAINS.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public SunpetalGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Sunpetal Grove enters the battlefield tapped unless you control a Forest or a Plains.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private SunpetalGrove(final SunpetalGrove card) {
        super(card);
    }

    @Override
    public SunpetalGrove copy() {
        return new SunpetalGrove(this);
    }
}
