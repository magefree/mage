package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DragonScales extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with mana value 6 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public DragonScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
                
        // Enchanted creature gets +1/+2 and has vigilance.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 2, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA)
                .setText("and has vigilance"));
        this.addAbility(ability);
        
        // When a creature with converted mana cost 6 or greater enters the battlefield, you may return Dragon Scales from your graveyard to the battlefield attached to that creature.
        this.addAbility(new EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(filter));
    }

    private DragonScales(final DragonScales card) {
        super(card);
    }

    @Override
    public DragonScales copy() {
        return new DragonScales(this);
    }
}
