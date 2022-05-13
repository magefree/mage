

package mage.game.permanent.token;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public final class GutterGrimeToken extends TokenImpl {

    public GutterGrimeToken() {
        this ((UUID)null);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public GutterGrimeToken(UUID sourceId) {
        super("Ooze Token", "green Ooze creature token with \"This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new GutterGrimeCounters(sourceId), Duration.WhileOnBattlefield)));
    }

    public GutterGrimeToken(final GutterGrimeToken token) {
        super(token);
    }

    public GutterGrimeToken copy() {
        return new GutterGrimeToken(this);
    }

    class GutterGrimeCounters implements DynamicValue {

        private final UUID sourceId;

        public GutterGrimeCounters(UUID sourceId) {
            this.sourceId = sourceId;
        }

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            Permanent p = game.getPermanent(sourceId);
            if (p != null) {
                return p.getCounters(game).getCount(CounterType.SLIME);
            }
            return 0;
        }

        @Override
        public GutterGrimeCounters copy() {
            return this;
        }

        @Override
        public String getMessage() {
            return "slime counters on Gutter Grime";
        }

        @Override
        public String toString() {
            return "1";
        }
    }
}
