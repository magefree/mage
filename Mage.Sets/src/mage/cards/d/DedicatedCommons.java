package mage.cards.d;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author muz
 */
public final class DedicatedCommons extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public DedicatedCommons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a planeswalker.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private DedicatedCommons(final DedicatedCommons card) {
        super(card);
    }

    @Override
    public DedicatedCommons copy() {
        return new DedicatedCommons(this);
    }
}
