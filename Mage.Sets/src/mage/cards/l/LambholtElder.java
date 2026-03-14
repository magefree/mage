package mage.cards.l;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LambholtElder extends TransformingDoubleFacedCard {

    public LambholtElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{G}",
                "Silverpelt Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Lambholt Elder
        this.getLeftHalfCard().setPT(1, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Elder.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Silverpelt Werewolf
        this.getRightHalfCard().setPT(4, 5);

        // Whenever Silverpelt Werewolf deals combat damage to a player, draw a card.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Silverpelt Werewolf.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private LambholtElder(final LambholtElder card) {
        super(card);
    }

    @Override
    public LambholtElder copy() {
        return new LambholtElder(this);
    }
}