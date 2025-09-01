package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class UltimeciaOmnipotent extends CardImpl {

    public UltimeciaOmnipotent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.color.setBlack(true);
        this.color.setBlue(true);

        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Time Compression -- When this creature transforms into Ultimecia, Omnipotent, take an extra turn after this one.
        this.addAbility(new TransformIntoSourceTriggeredAbility(new AddExtraTurnControllerEffect()).withFlavorWord("Time Compression"));
    }

    private UltimeciaOmnipotent(final UltimeciaOmnipotent card) {
        super(card);
    }

    @Override
    public UltimeciaOmnipotent copy() {
        return new UltimeciaOmnipotent(this);
    }
}
