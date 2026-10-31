package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class DrPulaskiCynicalClinician extends CardImpl {

    public DrPulaskiCynicalClinician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Seek out -- Whenever you draw your second card each turn, you gain 4 life.
        this.addAbility(new DrawNthCardTriggeredAbility(new GainLifeEffect(4)).withFlavorWord("Seek out"));
    }

    private DrPulaskiCynicalClinician(final DrPulaskiCynicalClinician card) {
        super(card);
    }

    @Override
    public DrPulaskiCynicalClinician copy() {
        return new DrPulaskiCynicalClinician(this);
    }
}
