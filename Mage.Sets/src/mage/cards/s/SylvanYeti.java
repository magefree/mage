
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SylvanYeti extends CardImpl {

    public SylvanYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Sylvan Yeti's power is equal to the number of cards in your hand.
        Effect effect = new SetBasePowerSourceEffect(CardsInControllerHandCount.instance);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    private SylvanYeti(final SylvanYeti card) {
        super(card);
    }

    @Override
    public SylvanYeti copy() {
        return new SylvanYeti(this);
    }
}
