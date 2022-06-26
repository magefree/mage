package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SwordOfSinewAndSteel extends CardImpl {

    public SwordOfSinewAndSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from black and from red.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED), AttachmentType.EQUIPMENT
        ).setText("and has protection from black and from red"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, destroy up to one target planeswalker and up to one target artifact.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DestroyTargetEffect(false, true)
                        .setText("destroy up to one target planeswalker and up to one target artifact."),
                "equipped creature", false
        );
        ability.addTarget(new TargetPlaneswalkerPermanent(0, 1));
        ability.addTarget(new TargetArtifactPermanent(0, 1));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private SwordOfSinewAndSteel(final SwordOfSinewAndSteel card) {
        super(card);
    }

    @Override
    public SwordOfSinewAndSteel copy() {
        return new SwordOfSinewAndSteel(this);
    }
}
