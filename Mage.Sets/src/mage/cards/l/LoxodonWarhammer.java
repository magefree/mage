
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author anonymous
 */
public final class LoxodonWarhammer extends CardImpl {

    public LoxodonWarhammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0 and has trample and lifelink. (If the creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker. Damage dealt by the creature also causes its controller to gain that much life.)
        Effect effect = new BoostEquippedEffect(3, 0);
        effect.setText("Equipped creature gets +3/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and lifelink. <i>(If the creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker. Damage dealt by the creature also causes its controller to gain that much life.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip (: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private LoxodonWarhammer(final LoxodonWarhammer card) {
        super(card);
    }

    @Override
    public LoxodonWarhammer copy() {
        return new LoxodonWarhammer(this);
    }
}
