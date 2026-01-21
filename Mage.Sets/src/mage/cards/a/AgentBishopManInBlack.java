package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentBishopManInBlack extends CardImpl {

    public AgentBishopManInBlack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private AgentBishopManInBlack(final AgentBishopManInBlack card) {
        super(card);
    }

    @Override
    public AgentBishopManInBlack copy() {
        return new AgentBishopManInBlack(this);
    }
}
