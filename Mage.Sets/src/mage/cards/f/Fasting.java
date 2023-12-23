
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class Fasting extends CardImpl {

    public Fasting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // At the beginning of your upkeep, put a hunger counter on Fasting. Then destroy Fasting if it has five or more hunger counters on it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.HUNGER.createInstance()), TargetController.YOU, false);
        ability.addEffect(new ConditionalOneShotEffect(new DestroySourceEffect(), new SourceHasCounterCondition(CounterType.HUNGER, 5), "Then destroy {this} if it has five or more hunger counters on it"));
        this.addAbility(ability);

        // If you would begin your draw step, you may skip that step instead. If you do, you gain 2 life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FastingReplacementEffect()));
        
        // When you draw a card, destroy Fasting.
        this.addAbility(new DrawCardControllerTriggeredAbility(new DestroySourceEffect(), false));

    }

    private Fasting(final Fasting card) {
        super(card);
    }

    @Override
    public Fasting copy() {
        return new Fasting(this);
    }
}

class FastingReplacementEffect extends ReplacementEffectImpl {

    public FastingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would begin your draw step, you may skip that step instead. If you do, you gain 2 life";
    }

    private FastingReplacementEffect(final FastingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FastingReplacementEffect copy() {
        return new FastingReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (event.getPlayerId().equals(source.getControllerId())
                && controller != null
                && controller.chooseUse(outcome, "Skip your draw step to gain 2 life?", source, game)) {
            controller.gainLife(2, game, source);
            return true;
        }
        return false;
    }
}
