package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class LambholtButcher extends CardImpl {

    public LambholtButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lambholt Butcher.
       this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private LambholtButcher(final LambholtButcher card) {
        super(card);
    }

    @Override
    public LambholtButcher copy() {
        return new LambholtButcher(this);
    }
}
