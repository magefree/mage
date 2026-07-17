package mage.cards.r;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResurrectionOrb extends CardImpl {

    public ResurrectionOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Whenever equipped creature dies, return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false),
                        TargetController.ANY
                )).setText("return that card to the battlefield under its owner's control at the beginning of the next end step"),
                "equipped creature", false, true, SetTargetPointer.CARD));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private ResurrectionOrb(final ResurrectionOrb card) {
        super(card);
    }

    @Override
    public ResurrectionOrb copy() {
        return new ResurrectionOrb(this);
    }
}
