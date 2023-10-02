

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.ZuberasDiedDynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 *
 * @author Loki
 */
public final class EmberFistZubera extends CardImpl {

    public EmberFistZubera (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(ZuberasDiedDynamicValue.instance));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability, new ZuberasDiedWatcher());
    }

    private EmberFistZubera(final EmberFistZubera card) {
        super(card);
    }

    @Override
    public EmberFistZubera copy() {
        return new EmberFistZubera(this);
    }

}

