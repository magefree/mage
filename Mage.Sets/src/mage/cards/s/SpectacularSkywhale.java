package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectacularSkywhale extends CardImpl {

    public SpectacularSkywhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Opus -- Whenever you cast an instant or sorcery spell, this creature gets +3/+0 until end of turn. If five or more mana was spent to cast that spell, put three +1/+1 counters on this creature instead.
        this.addAbility(new OpusAbility(
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "this creature " +
                "gets +3/+0 until end of turn. If five or more mana was spent to cast that spell, " +
                "put three +1/+1 counters on this creature instead", true
        ));
    }

    private SpectacularSkywhale(final SpectacularSkywhale card) {
        super(card);
    }

    @Override
    public SpectacularSkywhale copy() {
        return new SpectacularSkywhale(this);
    }
}
