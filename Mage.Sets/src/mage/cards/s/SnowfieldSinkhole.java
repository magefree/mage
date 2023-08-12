package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class SnowfieldSinkhole extends CardImpl {

    public SnowfieldSinkhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // {tap}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // Snowfield Sinkhole enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private SnowfieldSinkhole(final SnowfieldSinkhole card) {
        super(card);
    }

    @Override
    public SnowfieldSinkhole copy() {
        return new SnowfieldSinkhole(this);
    }
}
