package mage.cards.w;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WindsOfQalSisma extends CardImpl {

    public WindsOfQalSisma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Prevent all combat damage that would be dealt this turn.
        // Ferocious - If you control a creature with power 4 or greater, instead prevent all combat damage that would be dealt this turn by creatures your opponents control.
        Effect effect = new ConditionalReplacementEffect(
                new PreventAllDamageByAllPermanentsEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, Duration.EndOfTurn, true),
                new LockedInCondition(FerociousCondition.instance),
                new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        effect.setText("Prevent all combat damage that would be dealt this turn.<br>" +
                "<i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, instead prevent all combat damage that would be dealt this turn by creatures your opponents control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private WindsOfQalSisma(final WindsOfQalSisma card) {
        super(card);
    }

    @Override
    public WindsOfQalSisma copy() {
        return new WindsOfQalSisma(this);
    }
}
