package mage.cards.v;

import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class VillagersOfEstwald extends TransformingDoubleFacedCard {

    public VillagersOfEstwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{G}",
                "Howlpack of Estwald",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Villagers of Estwald
        this.getLeftHalfCard().setPT(2, 3);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Villagers of Estwald.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Howlpack of Estwald
        this.getRightHalfCard().setPT(4, 6);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack of Estwald.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private VillagersOfEstwald(final VillagersOfEstwald card) {
        super(card);
    }

    @Override
    public VillagersOfEstwald copy() {
        return new VillagersOfEstwald(this);
    }
}
