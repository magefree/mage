

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SerumRaker extends CardImpl {

    public SerumRaker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new DiscardEachPlayerEffect()));
    }

    private SerumRaker(final SerumRaker card) {
        super(card);
    }

    @Override
    public SerumRaker copy() {
        return new SerumRaker(this);
    }

}
