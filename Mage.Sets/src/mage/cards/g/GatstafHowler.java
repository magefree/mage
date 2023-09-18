package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GatstafHowler extends CardImpl {

    public GatstafHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(IntimidateAbility.getInstance());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Gatstaf Howler.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private GatstafHowler(final GatstafHowler card) {
        super(card);
    }

    @Override
    public GatstafHowler copy() {
        return new GatstafHowler(this);
    }
}
