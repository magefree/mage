package mage.cards.t;

import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Transcendence extends CardImpl {

    private static final DynamicValue value = new MultipliedValue(SavedLifeLossValue.MUCH, 2);

    public Transcendence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}{W}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // When you have 20 or more life, you lose the game.
        this.addAbility(new TranscendenceStateTriggeredAbility());

        // Whenever you lose life, you gain 2 life for each 1 life you lost.
        this.addAbility(new LoseLifeTriggeredAbility(
                new GainLifeEffect(value).setText("you gain 2 life for each 1 life you lost")
        ));
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
