
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Transcendence extends CardImpl {

    public Transcendence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}{W}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // When you have 20 or more life, you lose the game.
        this.addAbility(new TranscendenceStateTriggeredAbility());

        // Whenever you lose life, you gain 2 life for each 1 life you lost.
        this.addAbility(new TranscendenceLoseLifeTriggeredAbility());
    }

    private Transcendence(final Transcendence card) {
        super(card);
    }

    @Override
    public Transcendence copy() {
        return new Transcendence(this);
    }
}

class TranscendenceStateTriggeredAbility extends StateTriggeredAbility {

    TranscendenceStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseGameSourceControllerEffect());
    }

    private TranscendenceStateTriggeredAbility(final TranscendenceStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TranscendenceStateTriggeredAbility copy() {
        return new TranscendenceStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            return controller.getLife() >= 20;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you have 20 or more life, you lose the game.";
    }
}

class TranscendenceLoseLifeTriggeredAbility extends TriggeredAbilityImpl {

    TranscendenceLoseLifeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TranscendenceLoseLifeEffect(), false);
    }

    private TranscendenceLoseLifeTriggeredAbility(final TranscendenceLoseLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TranscendenceLoseLifeTriggeredAbility copy() {
        return new TranscendenceLoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof TranscendenceLoseLifeEffect) {
                    ((TranscendenceLoseLifeEffect) effect).setAmount(event.getAmount());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you lose life, you gain 2 life for each 1 life you lost.";
    }
}

class TranscendenceLoseLifeEffect extends OneShotEffect {

    private int amount = 0;

    TranscendenceLoseLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain 2 life for each 1 life you lost";
    }

    private TranscendenceLoseLifeEffect(final TranscendenceLoseLifeEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public TranscendenceLoseLifeEffect copy() {
        return new TranscendenceLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(2 * amount, game, source);
            return true;
        }
        return false;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
