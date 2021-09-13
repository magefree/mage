package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GatstafRavagers extends CardImpl {

    public GatstafRavagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        this.color.setRed(true);

        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Gatstaf Ravagers.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private GatstafRavagers(final GatstafRavagers card) {
        super(card);
    }

    @Override
    public GatstafRavagers copy() {
        return new GatstafRavagers(this);
    }
}
