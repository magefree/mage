
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class ValakutFireboar extends CardImpl {

    public ValakutFireboar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(7);

        this.addAbility(new AttacksTriggeredAbility(new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn).setText("switch its power and toughness until end of turn"), false));
    }

    private ValakutFireboar(final ValakutFireboar card) {
        super(card);
    }

    @Override
    public ValakutFireboar copy() {
        return new ValakutFireboar(this);
    }
}