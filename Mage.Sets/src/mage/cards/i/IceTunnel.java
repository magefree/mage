package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class IceTunnel extends CardImpl {

    public IceTunnel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // {tap}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // Ice Tunnel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private IceTunnel(final IceTunnel card) {
        super(card);
    }

    @Override
    public IceTunnel copy() {
        return new IceTunnel(this);
    }
}
