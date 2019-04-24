
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MasumaroFirstToLive extends CardImpl {

    public MasumaroFirstToLive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Masumaro, First to Live's power and toughness are each equal to twice the number of cards in your hand.
        DynamicValue xValue= new MultipliedValue(new CardsInControllerHandCount(), 2);
        Effect effect = new SetPowerToughnessSourceEffect(xValue, Duration.EndOfGame);
        effect.setText("{this}'s power and toughness are each equal to twice the number of cards in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));


    }

    public MasumaroFirstToLive(final MasumaroFirstToLive card) {
        super(card);
    }

    @Override
    public MasumaroFirstToLive copy() {
        return new MasumaroFirstToLive(this);
    }
}
