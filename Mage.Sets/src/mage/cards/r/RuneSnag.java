
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class RuneSnag extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Rune Snag"));
    }

    public RuneSnag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell unless its controller pays {2} plus an additional {2} for each card named Rune Snag in each graveyard.
        Effect effect = new CounterUnlessPaysEffect(new IntPlusDynamicValue(2, new MultipliedValue(new CardsInAllGraveyardsCount(filter), 2)));
        effect.setText("Counter target spell unless its controller pays {2} plus an additional {2} for each card named Rune Snag in each graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private RuneSnag(final RuneSnag card) {
        super(card);
    }

    @Override
    public RuneSnag copy() {
        return new RuneSnag(this);
    }
}
