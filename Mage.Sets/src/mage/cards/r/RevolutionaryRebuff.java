
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class RevolutionaryRebuff extends CardImpl {

    public RevolutionaryRebuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target nonartifact spell unless its controller pays 2.
        FilterSpell filter = new FilterSpell("nonartifact spell");
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
    }

    private RevolutionaryRebuff(final RevolutionaryRebuff card) {
        super(card);
    }

    @Override
    public RevolutionaryRebuff copy() {
        return new RevolutionaryRebuff(this);
    }
}
