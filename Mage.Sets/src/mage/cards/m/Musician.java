package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DynamicValueGenericManaCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Musician extends CardImpl {

    public Musician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // {tap}: Put a music counter on target creature. If it doesn't have "At the beginning of your upkeep, destroy this creature unless you pay {1} for each music counter on it," it gains that ability.
        Effect effect = new DoUnlessControllerPaysEffect(
                new DestroySourceEffect(),
                new DynamicValueGenericManaCost(
                        new CountersSourceCount(CounterType.MUSIC),
                        "{1} for each music counter on {this}"));
        effect.setText("destroy this creature unless you pay {1} for each music counter on it");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                effect,
                TargetController.YOU,
                false,
                false,
                "At the beginning of your upkeep, ");
        Effect effect2 = new AddCountersTargetEffect(CounterType.MUSIC.createInstance());
        effect2.setText("Put a music counter on target creature");
        Effect effect3 = new GainAbilityTargetEffect(
                ability,
                Duration.WhileOnBattlefield);
        effect3.setText("If it doesn't have \"At the beginning of your upkeep, destroy this creature unless you pay {1} for each music counter on it,\" it gains that ability");
        Ability ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                effect2,
                new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addEffect(effect3);
        this.addAbility(ability2);

    }

    private Musician(final Musician card) {
        super(card);
    }

    @Override
    public Musician copy() {
        return new Musician(this);
    }
}

