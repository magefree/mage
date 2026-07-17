package mage.cards.g;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GatstafArsonists extends TransformingDoubleFacedCard {

    public GatstafArsonists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{R}",
                "Gatstaf Ravagers",
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Gatstaf Arsonists
        this.getLeftHalfCard().setPT(5, 4);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Gatstaf Arsonists.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Gatstaf Ravagers
        this.getRightHalfCard().setPT(6, 5);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Gatstaf Ravagers.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private GatstafArsonists(final GatstafArsonists card) {
        super(card);
    }

    @Override
    public GatstafArsonists copy() {
        return new GatstafArsonists(this);
    }
}
