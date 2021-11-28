package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class MoonscarredWerewolf extends CardImpl {

    public MoonscarredWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.addAbility(VigilanceAbility.getInstance());

        // {tap}: Add {G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Moonscarred Werewolf.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private MoonscarredWerewolf(final MoonscarredWerewolf card) {
        super(card);
    }

    @Override
    public MoonscarredWerewolf copy() {
        return new MoonscarredWerewolf(this);
    }
}
