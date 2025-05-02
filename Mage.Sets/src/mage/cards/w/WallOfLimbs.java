package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WallOfLimbs extends CardImpl {

    public WallOfLimbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on Wall of Limbs.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // {5}{B}{B}, Sacrifice Wall of Limbs: Target player loses X life, where X is Wall of Limbs's power.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE), new ManaCostsImpl<>("{5}{B}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private WallOfLimbs(final WallOfLimbs card) {
        super(card);
    }

    @Override
    public WallOfLimbs copy() {
        return new WallOfLimbs(this);
    }
}
