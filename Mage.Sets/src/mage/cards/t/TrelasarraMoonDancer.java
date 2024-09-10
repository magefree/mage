package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrelasarraMoonDancer extends CardImpl {

    public TrelasarraMoonDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put a +1/+1 counter on Trelasarra Moon Dancer and scry 1.
        Ability ability = new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new ScryEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TrelasarraMoonDancer(final TrelasarraMoonDancer card) {
        super(card);
    }

    @Override
    public TrelasarraMoonDancer copy() {
        return new TrelasarraMoonDancer(this);
    }
}
