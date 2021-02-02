package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class CantoBightEnforcer extends CardImpl {

    public CantoBightEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Canto Bight Enforcer enters the battlefield, you may put a bounty counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Bounty - Whenever a creature an opponent controls with a bounty counter on it dies, put a +1/+1 counter on Canto Bight Enforcer.
        this.addAbility(new BountyAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private CantoBightEnforcer(final CantoBightEnforcer card) {
        super(card);
    }

    @Override
    public CantoBightEnforcer copy() {
        return new CantoBightEnforcer(this);
    }
}
