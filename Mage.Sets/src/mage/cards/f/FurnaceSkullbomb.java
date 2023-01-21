package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class FurnaceSkullbomb extends CardImpl {

    public FurnaceSkullbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, Sacrifice Furnace Skullbomb: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {1}{R}, Sacrifice Furnace Skullbomb: Put two oil counters on target artifact or creature you control. Draw a card. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.OIL.createInstance(2)), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private FurnaceSkullbomb(final FurnaceSkullbomb card) {
        super(card);
    }

    @Override
    public FurnaceSkullbomb copy() {
        return new FurnaceSkullbomb(this);
    }
}
