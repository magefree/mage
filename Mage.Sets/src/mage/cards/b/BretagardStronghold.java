package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class BretagardStronghold extends CardImpl {

    public BretagardStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Bretagard Stronghold enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {G}{W}{W}, {T}, Sacrifice Bretagard Stronghold: Put a +1/+1 counter on each of up to two target creatures you control. They gain vigilance and lifelink until end of turn. Activate this ability only any time you cast a sorcery.
        Effect countersEffect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        countersEffect.setText("Put a +1/+1 counter on each of up to two target creatures you control");

        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, countersEffect, new ManaCostsImpl<>("{G}{W}{W}"));

        Effect vigilanceEffect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        vigilanceEffect.setText("They gain vigilance");
        ability.addEffect(vigilanceEffect);

        Effect lifelinkEffect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        lifelinkEffect.setText("and lifelink until end of turn");
        ability.addEffect(lifelinkEffect);

        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 2));
        this.addAbility(ability);


    }

    private BretagardStronghold(final BretagardStronghold card) {
        super(card);
    }

    @Override
    public BretagardStronghold copy() {
        return new BretagardStronghold(this);
    }
}
