
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author anonymous
 */
public final class ChamberOfManipulation extends CardImpl {

    public ChamberOfManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted land has "{tap}, Discard a card: Gain control of target creature until end of turn."
        Ability controlAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        controlAbility.addTarget(new TargetCreaturePermanent());
        controlAbility.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(controlAbility, AttachmentType.AURA, 
                Duration.WhileOnBattlefield, "Enchanted land has \"{t}, Discard a card: Gain control of target creature until end of turn.\"")));
    }

    private ChamberOfManipulation(final ChamberOfManipulation card) {
        super(card);
    }

    @Override
    public ChamberOfManipulation copy() {
        return new ChamberOfManipulation(this);
    }
}
