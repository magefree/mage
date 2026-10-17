
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class PrecipiceOfMortis extends CardImpl {

    public PrecipiceOfMortis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}{W}");

        // If a Jedi entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers additional time
        this.addAbility(new SimpleStaticAbility(new PrecipiceOfMortisEffect()));

    }

    private PrecipiceOfMortis(final PrecipiceOfMortis card) {
        super(card);
    }

    @Override
    public PrecipiceOfMortis copy() {
        return new PrecipiceOfMortis(this);
    }
}

class PrecipiceOfMortisEffect extends ReplacementEffectImpl {

    PrecipiceOfMortisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a Jedi entering or leaving the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    private PrecipiceOfMortisEffect(final PrecipiceOfMortisEffect effect) {
        super(effect);
    }

    @Override
    public PrecipiceOfMortisEffect copy() {
        return new PrecipiceOfMortisEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Precipice of Mortis
            if (source.isControlledBy(event.getPlayerId())) {
                // enters triggers
                // Only for entering Jedis
                if (numberOfTriggersEvent.getEnteredPermanents(game)
                        .stream()
                        .anyMatch(permanent -> permanent.hasSubtype(SubType.JEDI, game))) {
                    // Only for triggers of permanents
                    if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                        return true;
                    }
                }

                // leaves triggers
                // Only for leaving Jedis
                if (numberOfTriggersEvent.getLeftPermanents(game)
                        .stream()
                        .anyMatch(permanent -> permanent.hasSubtype(SubType.JEDI, game))) {
                    // Only for triggers of permanents
                    if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
