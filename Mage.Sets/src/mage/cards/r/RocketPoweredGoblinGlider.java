package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RocketPoweredGoblinGlider extends CardImpl {

    public RocketPoweredGoblinGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, if it was cast from your graveyard, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget().withInterveningIf(CastFromGraveyardSourceCondition.instance));

        // Equipped creature gets +2/+0 and has flying and haste.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and haste"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));

        // Mayhem {2}
        this.addAbility(new MayhemAbility(this, "{2}"));
    }

    private RocketPoweredGoblinGlider(final RocketPoweredGoblinGlider card) {
        super(card);
    }

    @Override
    public RocketPoweredGoblinGlider copy() {
        return new RocketPoweredGoblinGlider(this);
    }
}
