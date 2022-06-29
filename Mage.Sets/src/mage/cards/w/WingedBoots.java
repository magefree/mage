package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class WingedBoots extends CardImpl {

    public WingedBoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and ward {4}.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(4)), AttachmentType.EQUIPMENT
        ).setText("and ward {4}. " +
                "<i>(Whenever equipped creature becomes the target of a spell or ability an opponent controls, " +
                "counter it unless that player pays {4}.)</i>"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }

    private WingedBoots(final WingedBoots card) {
        super(card);
    }

    @Override
    public WingedBoots copy() {
        return new WingedBoots(this);
    }
}
