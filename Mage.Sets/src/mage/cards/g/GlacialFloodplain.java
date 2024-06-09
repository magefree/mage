package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class GlacialFloodplain extends CardImpl {

    public GlacialFloodplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // {tap}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Glacial Floodplain enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private GlacialFloodplain(final GlacialFloodplain card) {
        super(card);
    }

    @Override
    public GlacialFloodplain copy() {
        return new GlacialFloodplain(this);
    }
}
