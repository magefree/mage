package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author muz
 */
public final class FormidableCommons extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public FormidableCommons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a planeswalker.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private FormidableCommons(final FormidableCommons card) {
        super(card);
    }

    @Override
    public FormidableCommons copy() {
        return new FormidableCommons(this);
    }
}
