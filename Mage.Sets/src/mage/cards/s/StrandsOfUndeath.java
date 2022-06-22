
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.RegenerateAttachedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author lopho
 */
public final class StrandsOfUndeath extends CardImpl {

    public StrandsOfUndeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        // When Strands of Undeath enters the battlefield, target player discards two cards.
        // {B}: Regenerate enchanted creature.
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateAttachedEffect(AttachmentType.AURA), new ManaCostsImpl<>("{B}")));
        
        Ability abilityDiscard = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        abilityDiscard.addTarget(new TargetPlayer());
        this.addAbility(abilityDiscard);
    }

    private StrandsOfUndeath(final StrandsOfUndeath card) {
        super(card);
    }

    @Override
    public StrandsOfUndeath copy() {
        return new StrandsOfUndeath(this);
    }
}
