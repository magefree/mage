package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssassinDen extends CardImpl {

    public AssassinDen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}{U}: Put a +1/+1 counter on target creature you control. It can't be blocked this turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn).setText("it can't be blocked this turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AssassinDen(final AssassinDen card) {
        super(card);
    }

    @Override
    public AssassinDen copy() {
        return new AssassinDen(this);
    }
}
