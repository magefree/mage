package mage.cards.s;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScatterRay extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact or creature spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public ScatterRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target artifact or creature spell unless its controller pays {4}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private ScatterRay(final ScatterRay card) {
        super(card);
    }

    @Override
    public ScatterRay copy() {
        return new ScatterRay(this);
    }
}
