
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author cbt33, LevelX2 (Sea's Claim), LevelX2 (Pollenbright Wings)
 */
public final class CausticTar extends CardImpl {

    public CausticTar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted land has "{T}: Target player loses 3 life."
        Ability tarAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(3), new TapSourceCost());
        tarAbility.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(tarAbility, AttachmentType.AURA, 
                Duration.WhileOnBattlefield,"Enchanted land has \"{T}: Target player loses 3 life.\"")));
        
    }

    private CausticTar(final CausticTar card) {
        super(card);
    }

    @Override
    public CausticTar copy() {
        return new CausticTar(this);
    }
}
