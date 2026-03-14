package mage.cards.k;

import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KessigForgemaster extends TransformingDoubleFacedCard {

    public KessigForgemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{1}{R}",
                "Flameheart Werewolf",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R");

        // Kessig Forgemaster
        this.getLeftHalfCard().setPT(2, 1);

        // Whenever Kessig Forgemaster blocks or becomes blocked by a creature, Kessig Forgemaster deals 1 damage to that creature.
        this.getLeftHalfCard().addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new DamageTargetEffect(1).withTargetDescription("that creature")
        ));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Kessig Forgemaster.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Flameheart Werewolf
        this.getRightHalfCard().setPT(3, 2);

        // Whenever Flameheart Werewolf blocks or becomes blocked by a creature, Flameheart Werewolf deals 2 damage to that creature.
        this.getRightHalfCard().addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new DamageTargetEffect(2).withTargetDescription("that creature")
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Flameheart Werewolf.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private KessigForgemaster(final KessigForgemaster card) {
        super(card);
    }

    @Override
    public KessigForgemaster copy() {
        return new KessigForgemaster(this);
    }
}
