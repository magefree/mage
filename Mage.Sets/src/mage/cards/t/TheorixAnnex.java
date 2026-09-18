package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author muz
 */
public final class TheorixAnnex extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public TheorixAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a planeswalker.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private TheorixAnnex(final TheorixAnnex card) {
        super(card);
    }

    @Override
    public TheorixAnnex copy() {
        return new TheorixAnnex(this);
    }
}
