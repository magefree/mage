package mage.cards.m;

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
public final class MercilessPredator extends CardImpl {

    public MercilessPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Merciless Predator.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private MercilessPredator(final MercilessPredator card) {
        super(card);
    }

    @Override
    public MercilessPredator copy() {
        return new MercilessPredator(this);
    }
}
