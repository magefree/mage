
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class WolfhuntersQuiver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf creature");

    static {
        filter.add(SubType.WEREWOLF.getPredicate());
    }

    public WolfhuntersQuiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{T}: This creature deals 1 damage to any target"
        Ability abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        abilityToGain.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityAttachedEffect(abilityToGain, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{T}: This creature deals 1 damage to any target\"");
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        
        // and "{T}: This creature deals 3 damage to target Werewolf creature."
        abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new TapSourceCost());
        abilityToGain.addTarget(new TargetCreaturePermanent(filter));
        effect = new GainAbilityAttachedEffect(abilityToGain, AttachmentType.EQUIPMENT);
        effect.setText("and \"{T}: This creature deals 3 damage to target Werewolf creature");
        ability.addEffect(effect);
        this.addAbility(ability);
        
        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5), false));
    }

    private WolfhuntersQuiver(final WolfhuntersQuiver card) {
        super(card);
    }

    @Override
    public WolfhuntersQuiver copy() {
        return new WolfhuntersQuiver(this);
    }
}
