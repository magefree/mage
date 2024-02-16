package mage.cards.w;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class WoodlandChasm extends CardImpl {

    public WoodlandChasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // {tap}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // Woodland Chasm enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private WoodlandChasm(final WoodlandChasm card) {
        super(card);
    }

    @Override
    public WoodlandChasm copy() {
        return new WoodlandChasm(this);
    }
}
