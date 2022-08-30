package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunlitMarsh extends CardImpl {

    public SunlitMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W} or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // Sunlit Marsh enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private SunlitMarsh(final SunlitMarsh card) {
        super(card);
    }

    @Override
    public SunlitMarsh copy() {
        return new SunlitMarsh(this);
    }
}
