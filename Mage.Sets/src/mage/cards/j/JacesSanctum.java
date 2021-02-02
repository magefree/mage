
package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class JacesSanctum extends CardImpl {
    
    
    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");
        
    private static final FilterSpell filter2 = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }
    
    static {
        filter2.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public JacesSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // Instant and sorcery spells you cast cost {1} less.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
        
        // Whenever you cast an instant or sorcery spell, scry 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ScryEffect(1), filter2, false));
    }

    private JacesSanctum(final JacesSanctum card) {
        super(card);
    }

    @Override
    public JacesSanctum copy() {
        return new JacesSanctum(this);
    }
}