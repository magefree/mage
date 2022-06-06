
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class InfernoFist extends CardImpl {

    public InfernoFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 0)));
        
        // {R}, Sacrifice Inferno Fist: Inferno Fist deals 2 damage to any target.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private InfernoFist(final InfernoFist card) {
        super(card);
    }

    @Override
    public InfernoFist copy() {
        return new InfernoFist(this);
    }
}
