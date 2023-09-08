

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent;

/**
 *
 * @author Loki
 */
public final class Putrefax extends CardImpl {

    public Putrefax (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect()));
    }

    private Putrefax(final Putrefax card) {
        super(card);
    }

    @Override
    public Putrefax copy() {
        return new Putrefax(this);
    }

}
