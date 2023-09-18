package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class BaneOfHanweir extends CardImpl {

    public BaneOfHanweir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Bane of Hanweir attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Bane of Hanweir.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private BaneOfHanweir(final BaneOfHanweir card) {
        super(card);
    }

    @Override
    public BaneOfHanweir copy() {
        return new BaneOfHanweir(this);
    }
}
