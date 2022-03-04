package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MoonriseIntruder extends CardImpl {

    public MoonriseIntruder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Moonrise Intruder.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private MoonriseIntruder(final MoonriseIntruder card) {
        super(card);
    }

    @Override
    public MoonriseIntruder copy() {
        return new MoonriseIntruder(this);
    }
}
