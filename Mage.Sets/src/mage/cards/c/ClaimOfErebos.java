
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ClaimOfErebos extends CardImpl {

    public ClaimOfErebos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has "{1}{B}, {T}: Target player loses 2 life."
        Ability grantedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{1}{B}"));
        grantedAbility.addCost(new TapSourceCost());
        grantedAbility.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(grantedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield)));
        
        
    }

    private ClaimOfErebos(final ClaimOfErebos card) {
        super(card);
    }

    @Override
    public ClaimOfErebos copy() {
        return new ClaimOfErebos(this);
    }
}
