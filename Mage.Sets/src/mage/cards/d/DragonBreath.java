package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class DragonBreath extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with mana value 6 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public DragonBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature has haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA)));
        
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostEnchantedEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
        
        // When a creature with converted mana cost 6 or greater enters the battlefield, you may return Dragon Breath from your graveyard to the battlefield attached to that creature.
        this.addAbility(new EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(filter));
    }

    private DragonBreath(final DragonBreath card) {
        super(card);
    }

    @Override
    public DragonBreath copy() {
        return new DragonBreath(this);
    }
}
