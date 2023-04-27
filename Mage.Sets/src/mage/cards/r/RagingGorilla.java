package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class RagingGorilla extends CardImpl {

    public RagingGorilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.APE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Raging Gorilla blocks or becomes blocked, it gets +2/-2 until end of turn.
        this.addAbility(new BlocksOrBlockedSourceTriggeredAbility(new BoostSourceEffect(2, -2, Duration.EndOfTurn, "it")));
    }

    private RagingGorilla(final RagingGorilla card) {
        super(card);
    }

    @Override
    public RagingGorilla copy() {
        return new RagingGorilla(this);
    }
}
