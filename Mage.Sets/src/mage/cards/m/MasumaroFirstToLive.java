
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MasumaroFirstToLive extends CardImpl {

    public MasumaroFirstToLive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Masumaro, First to Live's power and toughness are each equal to twice the number of cards in your hand.
        DynamicValue xValue= new MultipliedValue(CardsInControllerHandCount.instance, 2);
        Effect effect = new SetBasePowerToughnessSourceEffect(xValue);
        effect.setText("{this}'s power and toughness are each equal to twice the number of cards in your hand");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));


    }

    private MasumaroFirstToLive(final MasumaroFirstToLive card) {
        super(card);
    }

    @Override
    public MasumaroFirstToLive copy() {
        return new MasumaroFirstToLive(this);
    }
}
