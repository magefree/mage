
package mage.cards.w;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WarCadence extends CardImpl {

    public WarCadence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {X}{R}: This turn, creatures can't block unless their controller pays {X} for each blocking creature they control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WarCadenceReplacementEffect(), new ManaCostsImpl<>("{X}{R}")));

    }

    private WarCadence(final WarCadence card) {
        super(card);
    }

    @Override
    public WarCadence copy() {
        return new WarCadence(this);
    }
}

class WarCadenceReplacementEffect extends ReplacementEffectImpl {

    DynamicValue xCosts = ManacostVariableValue.REGULAR;

    WarCadenceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "This turn, creatures can't block unless their controller pays {X} for each blocking creature they control";
    }

    WarCadenceReplacementEffect(WarCadenceReplacementEffect effect) {
        super(effect);
        this.xCosts = effect.xCosts.copy();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }

        int amount = xCosts.calculate(game, source, this);
        if (amount <= 0) {
            return false;
        }

        String mana = "{" + amount + '}';
        ManaCostsImpl<ManaCost> cost = new ManaCostsImpl<>(mana);
        if (cost.canPay(source, source, event.getPlayerId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + mana + " to declare blocker?", source, game)) {
            return !cost.payOrRollback(source, game, source, event.getPlayerId());
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public WarCadenceReplacementEffect copy() {
        return new WarCadenceReplacementEffect(this);
    }

}
