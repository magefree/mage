
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureSubtypeCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author HanClinto
 */
public final class LavamancersSkill extends CardImpl {

    public LavamancersSkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature has "{tap}: This creature deals 1 damage to target creature."
        Ability pingAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        pingAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(pingAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, 
        "Enchanted creature has \"{T}: This creature deals 1 damage to target creature.\"")));
        
        // As long as enchanted creature is a Wizard, it has "{tap}: This creature deals 2 damage to target creature."
        Ability pingTwoAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        pingTwoAbility.addTarget(new TargetCreaturePermanent());
        ContinuousEffect isWizardEffect = new GainAbilityAttachedEffect(pingTwoAbility, AttachmentType.AURA);
        SimpleStaticAbility ifWizardAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(isWizardEffect, new EnchantedCreatureSubtypeCondition(SubType.WIZARD),
                "As long as enchanted creature is a Wizard, it has \"{T}: This creature deals 2 damage to target creature.\""));
        
        this.addAbility(ifWizardAbility);
    }

    private LavamancersSkill(final LavamancersSkill card) {
        super(card);
    }

    @Override
    public LavamancersSkill copy() {
        return new LavamancersSkill(this);
    }
}
