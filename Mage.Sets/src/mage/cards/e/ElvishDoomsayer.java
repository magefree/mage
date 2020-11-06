package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishDoomsayer extends CardImpl {

    public ElvishDoomsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elvish Doomsayer dies, each opponent discards a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private ElvishDoomsayer(final ElvishDoomsayer card) {
        super(card);
    }

    @Override
    public ElvishDoomsayer copy() {
        return new ElvishDoomsayer(this);
    }
}
