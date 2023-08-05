package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaladhrimBow extends CardImpl {

    public GaladhrimBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Galadhrim Bow enters the battlefield, attach it to target creature you control. Untap that creature.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new UntapTargetEffect("untap that creature"));
        this.addAbility(ability);

        // Equipped creature gets +1/+2 and has reach.
        ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has reach"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private GaladhrimBow(final GaladhrimBow card) {
        super(card);
    }

    @Override
    public GaladhrimBow copy() {
        return new GaladhrimBow(this);
    }
}
