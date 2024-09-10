package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
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
public final class AlpineMeadow extends CardImpl {

    public AlpineMeadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // {tap}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Alpine Meadow enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private AlpineMeadow(final AlpineMeadow card) {
        super(card);
    }

    @Override
    public AlpineMeadow copy() {
        return new AlpineMeadow(this);
    }
}
