package mage.cards.s;

import mage.Mana;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class ScornedVillager extends TransformingDoubleFacedCard {

    public ScornedVillager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{G}",
                "Moonscarred Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Scorned Villager
        this.getLeftHalfCard().setPT(1, 1);

        // {tap}: Add {G}.
        this.getLeftHalfCard().addAbility(new GreenManaAbility());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Scorned Villager.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Moonscarred Werewolf
        this.getRightHalfCard().setPT(2, 2);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // {tap}: Add {G}{G}.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost()));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Moonscarred Werewolf.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private ScornedVillager(final ScornedVillager card) {
        super(card);
    }

    @Override
    public ScornedVillager copy() {
        return new ScornedVillager(this);
    }
}
