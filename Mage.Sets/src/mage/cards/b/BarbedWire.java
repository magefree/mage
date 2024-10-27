package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BarbedWire extends CardImpl {

    public BarbedWire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, Barbed Wire deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1).withTargetDescription("that player"),
                TargetController.ACTIVE,
                false, true));

        // {2}: Prevent the next 1 damage that would be dealt by Barbed Wire this turn.
        this.addAbility(new SimpleActivatedAbility(
                new BarbedWirePreventionEffect(), new ManaCostsImpl<>("{2}")));

    }

    private BarbedWire(final BarbedWire card) {
        super(card);
    }

    @Override
    public BarbedWire copy() {
        return new BarbedWire(this);
    }
}

class BarbedWirePreventionEffect extends PreventionEffectImpl {

    BarbedWirePreventionEffect() {
        super(Duration.EndOfTurn, 1, false);
        staticText = "Prevent the next 1 damage that would be dealt by {this} this turn";
    }

    private BarbedWirePreventionEffect(final BarbedWirePreventionEffect effect) {
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
