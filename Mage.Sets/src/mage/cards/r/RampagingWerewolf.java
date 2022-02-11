package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class RampagingWerewolf extends CardImpl {

    public RampagingWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Rampaging Werewolf.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private RampagingWerewolf(final RampagingWerewolf card) {
        super(card);
    }

    @Override
    public RampagingWerewolf copy() {
        return new RampagingWerewolf(this);
    }
}
