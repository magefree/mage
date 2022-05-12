package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class BarbedWire extends CardImpl {

    private static final String rule = "At the beginning of each player's upkeep, "
            + "Barbed Wire deals 1 damage to that player.";

    public BarbedWire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, Barbed Wire deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new BarbwireDamageEffect(),
                TargetController.ACTIVE,
                false, true, rule));

        // {2}: Prevent the next 1 damage that would be dealt by Barbed Wire this turn.
        this.addAbility(new SimpleActivatedAbility(
                new BarbedWirePreventionEffect(), new ManaCostsImpl("{2}")));

    }

    private BarbedWire(final BarbedWire card) {
        super(card);
    }

    @Override
    public BarbedWire copy() {
        return new BarbedWire(this);
    }
}

class BarbwireDamageEffect extends OneShotEffect {

    public BarbwireDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "";
    }

    public BarbwireDamageEffect(final BarbwireDamageEffect effect) {
        super(effect);
    }

    @Override
    public BarbwireDamageEffect copy() {
        return new BarbwireDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (activePlayer != null) {
            activePlayer.damage(1, source.getSourceId(), source, game);
            return true;
        }

        return false;
    }
}

class BarbedWirePreventionEffect extends PreventionEffectImpl {

    public BarbedWirePreventionEffect() {
        super(Duration.EndOfTurn, 1, false);
        staticText = "Prevent the next 1 damage that would be dealt by {this} this turn";
    }

    public BarbedWirePreventionEffect(final BarbedWirePreventionEffect effect) {
        super(effect);
    }

    @Override
    public BarbedWirePreventionEffect copy() {
        return new BarbedWirePreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getSourceId().equals(source.getSourceId());
    }
}
