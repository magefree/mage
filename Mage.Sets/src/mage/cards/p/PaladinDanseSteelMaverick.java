package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class PaladinDanseSteelMaverick extends CardImpl {

    public PaladinDanseSteelMaverick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Exile Paladin Danse, Steel Maverick: Each creature you control that's an artifact or Human gains indestructible until end of turn.
    }

    private PaladinDanseSteelMaverick(final PaladinDanseSteelMaverick card) {
        super(card);
    }

    @Override
    public PaladinDanseSteelMaverick copy() {
        return new PaladinDanseSteelMaverick(this);
    }
}
