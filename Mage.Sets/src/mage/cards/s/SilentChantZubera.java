

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 *
 * @author Loki
 */
public final class SilentChantZubera extends CardImpl {

    public SilentChantZubera (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new DiesTriggeredAbility(new GainLifeEffect(new SilentChantZuberaDynamicValue()));
        this.addAbility(ability, new ZuberasDiedWatcher());
    }

    public SilentChantZubera (final SilentChantZubera card) {
        super(card);
    }

    @Override
    public SilentChantZubera copy() {
        return new SilentChantZubera(this);
    }

}

class SilentChantZuberaDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ZuberasDiedWatcher watcher = (ZuberasDiedWatcher) game.getState().getWatchers().get(ZuberasDiedWatcher.class.getSimpleName());
        return watcher.zuberasDiedThisTurn * 2;
    }

    @Override
    public SilentChantZuberaDynamicValue copy() {
        return new SilentChantZuberaDynamicValue();
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public String getMessage() {
        return "Zubera that died this turn";
    }
}
