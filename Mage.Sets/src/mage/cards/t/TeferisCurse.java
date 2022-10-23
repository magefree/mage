
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class TeferisCurse extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    
    static {
        filter.add(Predicates.or(
        CardType.CREATURE.getPredicate(),
        CardType.ARTIFACT.getPredicate()));
    }

    public TeferisCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted permanent has phasing.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(PhasingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, null, "permanent")));
    }

    private TeferisCurse(final TeferisCurse card) {
        super(card);
    }

    @Override
    public TeferisCurse copy() {
        return new TeferisCurse(this);
    }
}
