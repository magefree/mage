
package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
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
public final class GlacialFortress extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a Plains or an Island");

    static {
        filter.add(Predicates.or(SubType.PLAINS.getPredicate(), SubType.ISLAND.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public GlacialFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Glacial Fortress enters the battlefield tapped unless you control a Plains or an Island.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private GlacialFortress(final GlacialFortress card) {
        super(card);
    }

    @Override
    public GlacialFortress copy() {
        return new GlacialFortress(this);
    }
}
