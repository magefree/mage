
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Galatolol
 */
public final class Heartseeker extends CardImpl {

    public Heartseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1 and has "{tap}, Unattach Heartseeker: Destroy target creature."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Duration.WhileOnBattlefield));
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        gainedAbility.addCost(new UnattachCost(this.getName(), this.getId()));
        gainedAbility.addTarget(new TargetCreaturePermanent());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("and has \"{T}, Unattach {this}: Destroy target creature.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5)));
    }

    public Heartseeker(final Heartseeker card) {
        super(card);
    }

    @Override
    public Heartseeker copy() {
        return new Heartseeker(this);
    }
}
