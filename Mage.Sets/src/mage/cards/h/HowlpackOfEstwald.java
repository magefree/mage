package mage.cards.h;

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
public final class HowlpackOfEstwald extends CardImpl {

    public HowlpackOfEstwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack of Estwald.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private HowlpackOfEstwald(final HowlpackOfEstwald card) {
        super(card);
    }

    @Override
    public HowlpackOfEstwald copy() {
        return new HowlpackOfEstwald(this);
    }
}
