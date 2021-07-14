
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsPoisonCountersCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.InsectInfectToken;

/**
 *
 * @author North
 */
public final class PhyrexianSwarmlord extends CardImpl {

    public PhyrexianSwarmlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep",
                new CreateTokenEffect(new InsectInfectToken(), OpponentsPoisonCountersCount.instance)));
    }

    private PhyrexianSwarmlord(final PhyrexianSwarmlord card) {
        super(card);
    }

    @Override
    public PhyrexianSwarmlord copy() {
        return new PhyrexianSwarmlord(this);
    }
}

