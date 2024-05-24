package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodHustler extends CardImpl {

    public BloodHustler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you commit a crime, put a +1/+1 counter on Blood Hustler. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).setTriggersOnceEachTurn(true));

        // {3}{B}: Target opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{3}{B}"));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BloodHustler(final BloodHustler card) {
        super(card);
    }

    @Override
    public BloodHustler copy() {
        return new BloodHustler(this);
    }
}
