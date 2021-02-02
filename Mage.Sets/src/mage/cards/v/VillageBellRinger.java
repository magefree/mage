
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class VillageBellRinger extends CardImpl {

    private static final String rule = "untap all creatures you control";

    public VillageBellRinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(FlashAbility.getInstance());

        // When Village Bell-Ringer enters the battlefield, untap all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), rule), false));
    }

    private VillageBellRinger(final VillageBellRinger card) {
        super(card);
    }

    @Override
    public VillageBellRinger copy() {
        return new VillageBellRinger(this);
    }
}
