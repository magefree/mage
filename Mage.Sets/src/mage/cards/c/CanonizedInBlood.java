package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.VampireDemonToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CanonizedInBlood extends CardImpl {

    public CanonizedInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your end step, if you descended this turn, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU,
                DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability, new DescendedWatcher());

        // {5}{B}{B}, Sacrifice Canonized in Blood: Create a 4/3 white and black Vampire Demon creature token with flying.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new VampireDemonToken()),
                new ManaCostsImpl<>("{5}{B}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CanonizedInBlood(final CanonizedInBlood card) {
        super(card);
    }

    @Override
    public CanonizedInBlood copy() {
        return new CanonizedInBlood(this);
    }
}
