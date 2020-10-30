package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class OuterRimGang extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public OuterRimGang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Outer Rim Gang enters the battlefield, each creature with a bounty counter on it gets -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false)));
    }

    public OuterRimGang(final OuterRimGang card) {
        super(card);
    }

    @Override
    public OuterRimGang copy() {
        return new OuterRimGang(this);
    }
}
