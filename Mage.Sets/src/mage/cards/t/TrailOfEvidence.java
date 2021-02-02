
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class TrailOfEvidence extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public TrailOfEvidence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever you cast an instant or sorcery spell, investigate.
        this.addAbility(new SpellCastControllerTriggeredAbility(new InvestigateEffect(), filter, false));
    }

    private TrailOfEvidence(final TrailOfEvidence card) {
        super(card);
    }

    @Override
    public TrailOfEvidence copy() {
        return new TrailOfEvidence(this);
    }
}
