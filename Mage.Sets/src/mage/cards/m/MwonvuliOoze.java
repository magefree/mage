
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public final class MwonvuliOoze extends CardImpl {

    public MwonvuliOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{2}")));
        // Mwonvuli Ooze's power and toughness are each equal to 1 plus twice the number of age counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new MwonvuliOozePTValue(), Duration.EndOfGame)));
    }

    private MwonvuliOoze(final MwonvuliOoze card) {
        super(card);
    }

    @Override
    public MwonvuliOoze copy() {
        return new MwonvuliOoze(this);
    }
}


class MwonvuliOozePTValue extends CountersSourceCount {

    public MwonvuliOozePTValue() {
        super(CounterType.AGE);
    }

    public MwonvuliOozePTValue(final MwonvuliOozePTValue value) {
        super(value);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 2 * super.calculate(game, sourceAbility, effect) + 1;
    }

    @Override
    public MwonvuliOozePTValue copy() {
        return new MwonvuliOozePTValue(this);
    }

    @Override
    public String getMessage() {
        return "1 plus twice the number of age counters on it";
    }
}
