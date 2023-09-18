package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OneOfThePack extends CardImpl {

    public OneOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);
        this.color.setGreen(true);

        this.nightCard = true;

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform One of the Pack.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private OneOfThePack(final OneOfThePack card) {
        super(card);
    }

    @Override
    public OneOfThePack copy() {
        return new OneOfThePack(this);
    }
}
