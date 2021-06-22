package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Monoskelion extends CardImpl {

    public Monoskelion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Monoskelion enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), "with a +1/+1 counter on it"
        ));

        // {1}, Remove a +1/+1 counter from Monoskelion: Monoskelion deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1, "it"), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Monoskelion(final Monoskelion card) {
        super(card);
    }

    @Override
    public Monoskelion copy() {
        return new Monoskelion(this);
    }
}
