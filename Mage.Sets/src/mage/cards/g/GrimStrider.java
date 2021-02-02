
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Darkside-
 */
public final class GrimStrider extends CardImpl {

    public GrimStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Grim Strider gets -1/-1 for each card in your hand.
        DynamicValue count = new SignInversionDynamicValue(CardsInControllerHandCount.instance);
        Effect effect = new BoostSourceEffect(count, count, Duration.WhileOnBattlefield);
        effect.setText("{this} gets -1/-1 for each card in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private GrimStrider(final GrimStrider card) {
        super(card);
    }

    @Override
    public GrimStrider copy() {
        return new GrimStrider(this);
    }
}
