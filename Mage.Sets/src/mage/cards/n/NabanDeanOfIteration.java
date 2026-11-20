
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class NabanDeanOfIteration extends CardImpl {

    public NabanDeanOfIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new NabanDeanOfIterationEffect()));
    }

    private NabanDeanOfIteration(final NabanDeanOfIteration card) {
        super(card);
    }

    @Override
    public NabanDeanOfIteration copy() {
        return new NabanDeanOfIteration(this);
    }
}

class NabanDeanOfIterationEffect extends ReplacementEffectImpl {

    NabanDeanOfIterationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a Wizard you control entering causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    private NabanDeanOfIterationEffect(final NabanDeanOfIterationEffect effect) {
        super(effect);
    }

    @Override
    public NabanDeanOfIterationEffect copy() {
        return new NabanDeanOfIterationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Naban
            if (source.isControlledBy(event.getPlayerId())) {
                // Only for entering Wizards under your control
                if (numberOfTriggersEvent.getEnteredPermanents(game)
                        .stream()
                        .anyMatch(permanent -> permanent.hasSubtype(SubType.WIZARD, game)
                                && permanent.isControlledBy(source.getControllerId()))) {
                    // Only for triggers of permanents
                    return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
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
