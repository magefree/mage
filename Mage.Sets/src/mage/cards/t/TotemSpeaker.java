
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Wehk
 */
public final class TotemSpeaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Beast");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }    
    
    public TotemSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Beast enters the battlefield, you may gain 3 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), filter, true));
    }

    private TotemSpeaker(final TotemSpeaker card) {
        super(card);
    }

    @Override
    public TotemSpeaker copy() {
        return new TotemSpeaker(this);
    }
}
