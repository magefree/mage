
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class HungrySpriggan extends CardImpl {

    public HungrySpriggan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn).setText("it gets +3/+3 until end of turn"), false));
    }

    private HungrySpriggan(final HungrySpriggan card) {
        super(card);
    }

    @Override
    public HungrySpriggan copy() {
        return new HungrySpriggan(this);
    }
}
