
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 * @author nantuko
 */
public final class MaskOfAvacyn extends CardImpl {

    public MaskOfAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2 and has hexproof.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 2));
        Effect effect = new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has hexproof");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    private MaskOfAvacyn(final MaskOfAvacyn card) {
        super(card);
    }

    @Override
    public MaskOfAvacyn copy() {
        return new MaskOfAvacyn(this);
    }
}
