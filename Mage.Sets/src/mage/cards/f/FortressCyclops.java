
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class FortressCyclops extends CardImpl {

    public FortressCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Fortress Cyclops attacks, it gets +3/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn, "it")));
        // Whenever Fortress Cyclops blocks, it gets +0/+3 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(0, 3, Duration.EndOfTurn, "it")));
    }

    private FortressCyclops(final FortressCyclops card) {
        super(card);
    }

    @Override
    public FortressCyclops copy() {
        return new FortressCyclops(this);
    }
}
