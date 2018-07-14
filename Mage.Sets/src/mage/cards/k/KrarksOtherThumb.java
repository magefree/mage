
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author spjspj
 */
public final class KrarksOtherThumb extends CardImpl {

    public KrarksOtherThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        addSuperType(SuperType.LEGENDARY);

        // If you would roll a die, instead roll two of those dice and ignore one of those results.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KrarksOtherThumbEffect()));
    }

    public KrarksOtherThumb(final KrarksOtherThumb card) {
        super(card);
    }

    @Override
    public KrarksOtherThumb copy() {
        return new KrarksOtherThumb(this);
    }
}

class KrarksOtherThumbEffect extends ReplacementEffectImpl {

    KrarksOtherThumbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would roll a die, instead roll two die and ignore one";
    }

    KrarksOtherThumbEffect(final KrarksOtherThumbEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            // event.getData holds the num of sides of the die to roll
            String data = event.getData();
            int numSides = Integer.parseInt(data);
            int secondDieRoll = RandomUtil.nextInt(numSides) + 1;

            if (!game.isSimulation()) {
                game.informPlayers("[Roll a die] " + player.getLogName() + " rolled a " + secondDieRoll);
            }
            if (player.chooseUse(outcome, "Ignore the first die roll?", source, game)) {
                event.setAmount(secondDieRoll);
                game.informPlayers(player.getLogName() + " ignores the first die roll.");
            } else {
                game.informPlayers(player.getLogName() + " ignores the second die roll.");
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KrarksOtherThumbEffect copy() {
        return new KrarksOtherThumbEffect(this);
    }
}
