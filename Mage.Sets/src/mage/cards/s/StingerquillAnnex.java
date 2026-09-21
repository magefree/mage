package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author muz
 */
public final class StingerquillAnnex extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public StingerquillAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a planeswalker.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private StingerquillAnnex(final StingerquillAnnex card) {
        super(card);
    }

    @Override
    public StingerquillAnnex copy() {
        return new StingerquillAnnex(this);
    }
}
