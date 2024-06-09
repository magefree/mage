
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class FestivalOfTheGuildpact extends CardImpl {

    public FestivalOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // Prevent the next X damage that would be dealt to you this turn.
        this.getSpellAbility().addEffect(new PreventDamageToControllerEffect(Duration.EndOfTurn, false, true, ManacostVariableValue.REGULAR));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private FestivalOfTheGuildpact(final FestivalOfTheGuildpact card) {
        super(card);
    }

    @Override
    public FestivalOfTheGuildpact copy() {
        return new FestivalOfTheGuildpact(this);
    }
}
