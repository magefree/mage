

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.common.ZuberasDiedDynamicValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 * @author Loki
 */
public final class FloatingDreamZubera extends CardImpl {

    public FloatingDreamZubera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new DiesTriggeredAbility(new DrawCardSourceControllerEffect(new ZuberasDiedDynamicValue())), new ZuberasDiedWatcher());
    }

    public FloatingDreamZubera(final FloatingDreamZubera card) {
        super(card);
    }

    @Override
    public FloatingDreamZubera copy() {
        return new FloatingDreamZubera(this);
    }

}


