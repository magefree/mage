package mage.cards.c;

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
 * @author anonymous
 */
public final class ChubToad extends CardImpl {

    public ChubToad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Chub Toad blocks or becomes blocked, it gets +2/+2 until end of turn.
        this.addAbility(new BlocksOrBlockedSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn, "it")));
    }

    private ChubToad(final ChubToad card) {
        super(card);
    }

    @Override
    public ChubToad copy() {
        return new ChubToad(this);
    }
}
