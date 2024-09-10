
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class SenseiGoldenTail extends CardImpl {

    public SenseiGoldenTail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));

        // {1}{W}, {T}: Put a training counter on target creature. 
        // That creature gains bushido 1 and becomes a Samurai in addition to its other creature types. 
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.TRAINING.createInstance()),
                new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainAbilityTargetEffect(
                new BushidoAbility(1), Duration.Custom)
                .setText("That creature gains bushido 1")
        );
        ability.addEffect(
                new AddCardSubTypeTargetEffect(SubType.SAMURAI, Duration.Custom)
                        .setText("and becomes a Samurai in addition to its other creature types.")
        );
        this.addAbility(ability);
    }

    private SenseiGoldenTail(final SenseiGoldenTail card) {
        super(card);
    }

    @Override
    public SenseiGoldenTail copy() {
        return new SenseiGoldenTail(this);
    }

}
