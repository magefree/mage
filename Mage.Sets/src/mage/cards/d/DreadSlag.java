
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class DreadSlag extends CardImpl {

    public DreadSlag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Dread Slag gets -4/-4 for each card in your hand.
        DynamicValue amount = new MultipliedValue(CardsInControllerHandCount.instance, -4);
        Effect effect = new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield);
        effect.setText("{this} gets -4/-4 for each card in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private DreadSlag(final DreadSlag card) {
        super(card);
    }

    @Override
    public DreadSlag copy() {
        return new DreadSlag(this);
    }
}
