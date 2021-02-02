
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RunedServitor extends CardImpl {

    public RunedServitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardAllEffect(1), false));
    }

    private RunedServitor(final RunedServitor card) {
        super(card);
    }

    @Override
    public RunedServitor copy() {
        return new RunedServitor(this);
    }
}
