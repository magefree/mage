
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class ClamIAm extends CardImpl {

    public ClamIAm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.CLAMFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If you roll a 3 on a six-sided die, you may reroll that die.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ClamIAmEffect()));
    }

    public ClamIAm(final ClamIAm card) {
        super(card);
    }

    @Override
    public ClamIAm copy() {
        return new ClamIAm(this);
    }
}

class ClamIAmEffect extends ReplacementEffectImpl {

    ClamIAmEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you roll a 3 on a six-sided die, you may reroll that die";
    }

    ClamIAmEffect(final ClamIAmEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            String data = event.getData();
            int numSides = Integer.parseInt(data);
            if (numSides == 6 && event.getAmount() == 3) {
                if (player.chooseUse(outcome, "Reroll the die?", source, game)) {
                    game.informPlayers(player.getLogName() + " chose to reroll the die.");
                    event.setAmount(player.rollDice(game, 6));
                }
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
        return source.getControllerId().equals(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ClamIAmEffect copy() {
        return new ClamIAmEffect(this);
    }
}
