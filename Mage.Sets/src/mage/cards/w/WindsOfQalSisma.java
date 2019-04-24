
package mage.cards.w;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class WindsOfQalSisma extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public WindsOfQalSisma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Prevent all combat damage that would be dealt this turn.
        // Ferocious - If you control a creature with power 4 or greater, instead prevent all combat damage that would be dealt this turn by creatures your opponents control.
        Effect effect = new ConditionalReplacementEffect(
                new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true),
                new LockedInCondition(FerociousCondition.instance),
                new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        effect.setText("Prevent all combat damage that would be dealt this turn.<br>" +
                       "<i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, instead prevent all combat damage that would be dealt this turn by creatures your opponents control");
        this.getSpellAbility().addEffect(effect);
    }

    public WindsOfQalSisma(final WindsOfQalSisma card) {
        super(card);
    }

    @Override
    public WindsOfQalSisma copy() {
        return new WindsOfQalSisma(this);
    }
}
