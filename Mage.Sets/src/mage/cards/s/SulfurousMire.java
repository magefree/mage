package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class SulfurousMire extends CardImpl {

    public SulfurousMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // {tap}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Sulfurous Mire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private SulfurousMire(final SulfurousMire card) {
        super(card);
    }

    @Override
    public SulfurousMire copy() {
        return new SulfurousMire(this);
    }
}
