package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RegenerationsRestored extends CardImpl {

    public RegenerationsRestored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}");

        // Vanishing 12
        this.addAbility(new VanishingAbility(12));

        // Whenever one or more time counters are removed from Regenerations Restored, scry 1 and you gain 1 life. Then if Regenerations Restored has no time counters on it, exile it. When you do, take an extra turn after this one.
        this.addAbility(new RegenerationsRestoredTriggeredAbility());
    }

    private RegenerationsRestored(final RegenerationsRestored card) {
        super(card);
    }

    @Override
    public RegenerationsRestored copy() {
        return new RegenerationsRestored(this);
    }
}

class RegenerationsRestoredTriggeredAbility extends TriggeredAbilityImpl {

    RegenerationsRestoredTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(1, false));
        this.addEffect(new GainLifeEffect(1).concatBy("and"));

        this.addEffect(new ConditionalOneShotEffect(
                new RegenerationsRestoredEffect(),
                new SourceHasCounterCondition(CounterType.TIME, 0, 0)
        ).concatBy("Then"));

        this.setTriggerPhrase("Whenever one or more time counters are removed from {this}, ");
    }

    private RegenerationsRestoredTriggeredAbility(final RegenerationsRestoredTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getAmount() > 0
                && event.getData().equals(CounterType.TIME.getName())
                && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public RegenerationsRestoredTriggeredAbility copy() {
        return new RegenerationsRestoredTriggeredAbility(this);
    }
}

class RegenerationsRestoredEffect extends OneShotEffect {

    RegenerationsRestoredEffect() {
        super(Outcome.ExtraTurn);
        staticText = "exile it. When you do, take an extra turn after this one";
    }

    private RegenerationsRestoredEffect(final RegenerationsRestoredEffect effect) {
        super(effect);
    }

    @Override
    public RegenerationsRestoredEffect copy() {
        return new RegenerationsRestoredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (new ExileSourceEffect().apply(game, source)) {
            game.fireReflexiveTriggeredAbility(
                    new ReflexiveTriggeredAbility(new AddExtraTurnControllerEffect(), false)
                            .setTriggerPhrase("When you do, "),
                    source
            );
            return true;
        }
        return false;
    }

}