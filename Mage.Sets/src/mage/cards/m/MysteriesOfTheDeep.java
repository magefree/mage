
package mage.cards.m;

import java.util.UUID;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.LandfallWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class MysteriesOfTheDeep extends CardImpl {

    public MysteriesOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Draw two cards.
        // Landfall - If you had a land enter the battlefield under your control this turn, draw three cards instead.
        this.getSpellAbility().addWatcher(new LandfallWatcher());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(3), new DrawCardSourceControllerEffect(2), LandfallCondition.instance, "Draw 2 cards. Landfall - If you had a land enter the battlefield under your control this turn, draw three cards instead"));
    }

    private MysteriesOfTheDeep(final MysteriesOfTheDeep card) {
        super(card);
    }

    @Override
    public MysteriesOfTheDeep copy() {
        return new MysteriesOfTheDeep(this);
    }
}
