package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BlocksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShieldOfTheRighteous extends CardImpl {

    public ShieldOfTheRighteous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}{U}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+2 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // Whenever equipped creature blocks a creature, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new BlocksAttachedTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect("that creature"),
                "equipped", false, false, true
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetControlledCreaturePermanent()));
    }

    private ShieldOfTheRighteous(final ShieldOfTheRighteous card) {
        super(card);
    }

    @Override
    public ShieldOfTheRighteous copy() {
        return new ShieldOfTheRighteous(this);
    }
}
