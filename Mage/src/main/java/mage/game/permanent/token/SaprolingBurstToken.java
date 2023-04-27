
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public final class SaprolingBurstToken extends TokenImpl {

    public SaprolingBurstToken() {
        this((MageObjectReference)null);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SaprolingBurstToken(MageObjectReference saprolingBurstMOR) {
        super("Saproling Token", "green Saproling creature token with \"This creature's power and toughness are each equal to the number of fade counters on Saproling Burst.\"");
        this.color.setGreen(true);
        this.subtype.add(SubType.SAPROLING);
        this.cardType.add(CardType.CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetBasePowerToughnessSourceEffect(new SaprolingBurstTokenDynamicValue(saprolingBurstMOR))));
    }

    public SaprolingBurstToken(final SaprolingBurstToken token) {
        super(token);
    }

    public SaprolingBurstToken copy() {
        return new SaprolingBurstToken(this);
    }
}

class SaprolingBurstTokenDynamicValue implements DynamicValue {

    private final MageObjectReference saprolingBurstMOR;

    SaprolingBurstTokenDynamicValue(MageObjectReference saprolingBurstMOR) {
        this.saprolingBurstMOR = saprolingBurstMOR;
    }

    SaprolingBurstTokenDynamicValue(final SaprolingBurstTokenDynamicValue dynamicValue) {
        this.saprolingBurstMOR = dynamicValue.saprolingBurstMOR;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = this.saprolingBurstMOR.getPermanent(game);
        if (permanent != null) {
            return permanent.getCounters(game).getCount(CounterType.FADE);
        }
        return 0;
    }

    @Override
    public SaprolingBurstTokenDynamicValue copy() {
        return new SaprolingBurstTokenDynamicValue(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of fade counters on Saproling Burst";
    }
}
