
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CoralNet extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green or white creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.GREEN)));
    }
    
    static private final String rule = "Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature unless you discard a card.\"";

    public CoralNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);

        // Enchant green or white creature
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));
        
        // Enchanted creature has "At the beginning of your upkeep, sacrifice this creature unless you discard a card."
        Ability abilityToGain = new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new DiscardCardCost()), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAttachedEffect(abilityToGain, AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));
    }

    private CoralNet(final CoralNet card) {
        super(card);
    }

    @Override
    public CoralNet copy() {
        return new CoralNet(this);
    }
}
