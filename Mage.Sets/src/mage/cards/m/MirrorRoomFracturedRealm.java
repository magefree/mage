package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorRoomFracturedRealm extends RoomCard {

    public MirrorRoomFracturedRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{U}", "{5}{U}{U}");

        // Mirror Room
        // When you unlock this door, create a token that's a copy of target creature you control, except it's a Reflection in addition to its other creature types.
        Ability ability = new UnlockThisDoorTriggeredAbility(
                new CreateTokenCopyTargetEffect()
                        .withAdditionalSubType(SubType.REFLECTION)
                        .setText("create a token that's a copy of target creature you control, " +
                                "except it's a Reflection in addition to its other creature types"),
                false, true
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Fractured Realm
        // If a triggered ability of a permanent you control triggers, that ability triggers an additional time.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(StaticFilters.FILTER_CONTROLLED_A_PERMANENT)));
    }

    private MirrorRoomFracturedRealm(final MirrorRoomFracturedRealm card) {
        super(card);
    }

    @Override
    public MirrorRoomFracturedRealm copy() {
        return new MirrorRoomFracturedRealm(this);
    }
}
