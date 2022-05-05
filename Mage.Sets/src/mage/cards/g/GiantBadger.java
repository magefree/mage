package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class GiantBadger extends CardImpl {

    public GiantBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BADGER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Giant Badger blocks, it gets +2/+2 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn, "it")));
    }

    private GiantBadger(final GiantBadger card) {
        super(card);
    }

    @Override
    public GiantBadger copy() {
        return new GiantBadger(this);
    }
}
