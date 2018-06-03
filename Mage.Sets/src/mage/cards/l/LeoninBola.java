
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class LeoninBola extends CardImpl {

    public LeoninBola(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Leonin Bola: Tap target creature."
        Ability gainAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        gainAbility.addCost(new UnattachCost(this.getName(), this.getId()));
        gainAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainAbility, AttachmentType.EQUIPMENT)));
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public LeoninBola(final LeoninBola card) {
        super(card);
    }

    @Override
    public LeoninBola copy() {
        return new LeoninBola(this);
    }
}
