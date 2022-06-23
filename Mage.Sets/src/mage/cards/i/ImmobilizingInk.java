
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class ImmobilizingInk extends CardImpl {

    public ImmobilizingInk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                                                new GainAbilityAttachedEffect(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()),
                                                    AttachmentType.AURA,
                                                    Duration.WhileOnBattlefield,"Enchanted creature doesn't untap during its controller's untap step.")));
        
        // Enchanted creature has "{1}, Discard a card: Untap this creature."
        Ability untapAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl<>("{1}"));
        untapAbility.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                                                new GainAbilityAttachedEffect(untapAbility,
                                                    AttachmentType.AURA,
                                                    Duration.WhileOnBattlefield, "Enchanted creature has \"{1}, Discard a card: Untap this creature.\"")));
    }

    private ImmobilizingInk(final ImmobilizingInk card) {
        super(card);
    }

    @Override
    public ImmobilizingInk copy() {
        return new ImmobilizingInk(this);
    }
}
