
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class FireWhip extends CardImpl {

    public FireWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature has "{t}: This creature deals 1 damage to any target."
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability1.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability1, AttachmentType.AURA, Duration.WhileOnBattlefield, "Enchanted creature has \"{t}: This creature deals 1 damage to any target.\"")));
        // Sacrifice Fire Whip: Fire Whip deals 1 damage to any target.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new SacrificeSourceCost());
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);
        
    }

    private FireWhip(final FireWhip card) {
        super(card);
    }

    @Override
    public FireWhip copy() {
        return new FireWhip(this);
    }
}
