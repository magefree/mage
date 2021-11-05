package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TimberShredder extends CardImpl {

    public TimberShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Timber Shredder.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private TimberShredder(final TimberShredder card) {
        super(card);
    }

    @Override
    public TimberShredder copy() {
        return new TimberShredder(this);
    }
}
