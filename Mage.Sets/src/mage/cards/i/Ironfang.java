package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class Ironfang extends CardImpl {

    public Ironfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ironfang.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private Ironfang(final Ironfang card) {
        super(card);
    }

    @Override
    public Ironfang copy() {
        return new Ironfang(this);
    }
}
