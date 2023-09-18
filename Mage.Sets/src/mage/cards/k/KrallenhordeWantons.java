package mage.cards.k;

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
public final class KrallenhordeWantons extends CardImpl {

    public KrallenhordeWantons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Wantons.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private KrallenhordeWantons(final KrallenhordeWantons card) {
        super(card);
    }

    @Override
    public KrallenhordeWantons copy() {
        return new KrallenhordeWantons(this);
    }
}
