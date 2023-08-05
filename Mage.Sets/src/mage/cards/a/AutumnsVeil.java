package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.CantBeTargetedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AutumnsVeil extends CardImpl {

    private static final FilterSpell filterTarget1 = new FilterSpell("spells you control");
    private static final FilterSpell filterSource = new FilterSpell("blue or black spells");

    static {
        filterSource.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)));
    }

    public AutumnsVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Spells you control can't be countered by blue or black spells this turn
        this.getSpellAbility().addEffect(new CantBeCounteredControlledEffect(filterTarget1, filterSource, Duration.EndOfTurn));

        // and creatures you control can't be the targets of blue or black spells this turn.
        this.getSpellAbility().addEffect(new CantBeTargetedAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES, filterSource, Duration.EndOfTurn)
                .concatBy(", and"));
    }

    private AutumnsVeil(final AutumnsVeil card) {
        super(card);
    }

    @Override
    public AutumnsVeil copy() {
        return new AutumnsVeil(this);
    }

}
