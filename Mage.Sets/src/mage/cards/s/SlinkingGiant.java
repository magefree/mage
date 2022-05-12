
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class SlinkingGiant extends CardImpl {

    public SlinkingGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(WitherAbility.getInstance());
        // Whenever Slinking Giant blocks or becomes blocked, it gets -3/-0 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(-3, 0, Duration.EndOfTurn).setText("it gets -3/-0 until end of turn"), false).setTriggerPhrase("Whenever {this} blocks or becomes blocked, "));
    }

    private SlinkingGiant(final SlinkingGiant card) {
        super(card);
    }

    @Override
    public SlinkingGiant copy() {
        return new SlinkingGiant(this);
    }
}
