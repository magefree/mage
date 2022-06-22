
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class MercilessJavelineer extends CardImpl {

    public MercilessJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {2}, Discard a card: Put a -1/-1 counter on target creature. That creature can't block this turn.
        Ability ability =
                new SimpleActivatedAbility(
                        Zone.BATTLEFIELD,
                        new AddCountersTargetEffect(
                                CounterType.M1M1.createInstance(),
                                StaticValue.get(1),
                                Outcome.Removal),
                        new ManaCostsImpl<>("{2}"));
        ability.addEffect(
                new CantBlockTargetEffect(Duration.EndOfTurn)
                        .setText("That creature can't block this turn."));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        addAbility(ability);
    }

    private MercilessJavelineer(final MercilessJavelineer card) {
        super(card);
    }

    @Override
    public MercilessJavelineer copy() {
        return new MercilessJavelineer(this);
    }
}
