package mage.cards.t;

import java.util.UUID;

import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author muz
 */
public final class TempleOfKhonshu extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DIMENSION);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);


    public TempleOfKhonshu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a Dimension.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private TempleOfKhonshu(final TempleOfKhonshu card) {
        super(card);
    }

    @Override
    public TempleOfKhonshu copy() {
        return new TempleOfKhonshu(this);
    }
}
