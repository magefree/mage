
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ImperialCeratops extends CardImpl {

    public ImperialCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // <i>Enrage</i> &mdash; Whenever Imperial Ceratops is dealt damage, you gain 2 life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(2), false, true));
    }

    public ImperialCeratops(final ImperialCeratops card) {
        super(card);
    }

    @Override
    public ImperialCeratops copy() {
        return new ImperialCeratops(this);
    }
}
