package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroodguardElite extends CardImpl {

    public BroodguardElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with X+1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When this creature leaves the battlefield, put its counters on target creature you control.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new PutSourceCountersOnTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Warp {X}{G}
        this.addAbility(new WarpAbility(this, "{X}{G}"));
    }

    private BroodguardElite(final BroodguardElite card) {
        super(card);
    }

    @Override
    public BroodguardElite copy() {
        return new BroodguardElite(this);
    }
}
