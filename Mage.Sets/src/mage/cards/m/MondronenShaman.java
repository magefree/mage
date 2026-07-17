package mage.cards.m;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class MondronenShaman extends TransformingDoubleFacedCard {

    public MondronenShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF}, "{3}{R}",
                "Tovolar's Magehunter",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Mondronen Shaman
        this.getLeftHalfCard().setPT(3, 2);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mondronen Shaman.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Tovolar's Magehunter
        this.getRightHalfCard().setPT(5, 5);

        // Whenever an opponent casts a spell, Tovolar's Magehunter deals 2 damage to that player.
        this.getRightHalfCard().addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(2).withTargetDescription("that player"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Tovolar's Magehunter.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private MondronenShaman(final MondronenShaman card) {
        super(card);
    }

    @Override
    public MondronenShaman copy() {
        return new MondronenShaman(this);
    }
}
