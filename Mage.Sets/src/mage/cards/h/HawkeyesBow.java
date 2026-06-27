package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class HawkeyesBow extends CardImpl {

    public HawkeyesBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has reach.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
            ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has reach"));
        this.addAbility(ability);

        // Whenever equipped creature becomes tapped, it deals 1 damage to each opponent.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(
            new DamagePlayersEffect(1, TargetController.OPPONENT, "it"),
            "equipped creature"
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private HawkeyesBow(final HawkeyesBow card) {
        super(card);
    }

    @Override
    public HawkeyesBow copy() {
        return new HawkeyesBow(this);
    }
}
