
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class Deathgreeter extends CardImpl {

    public Deathgreeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature dies, you may gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(1), true, true));
    }

    private Deathgreeter(final Deathgreeter card) {
        super(card);
    }

    @Override
    public Deathgreeter copy() {
        return new Deathgreeter(this);
    }
}
