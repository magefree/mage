package mage.cards.s;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwallowedByLeviathan extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD);

    public SwallowedByLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose target spell. Surveil 2, then counter the chosen spell unless its controller pays {1} for each card in your graveyard.
        this.getSpellAbility().addEffect(new InfoEffect("Choose target spell"));
        this.getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(xValue)
                .setText(", then counter the chosen spell unless its controller pays {1} for each card in your graveyard"));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SwallowedByLeviathan(final SwallowedByLeviathan card) {
        super(card);
    }

    @Override
    public SwallowedByLeviathan copy() {
        return new SwallowedByLeviathan(this);
    }
}
