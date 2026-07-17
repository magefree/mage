package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 *
 * @author muz
 */
public final class CoinOfMastery extends CardImpl {

    public CoinOfMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Each creature you control enters with an additional +1/+1 counter on it for each mana from an artifact source spent to cast it.
        this.addAbility(new SimpleStaticAbility(new CoinOfMasteryEffect()));

        // {T}: Create a Treasure token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new TapSourceCost()));
    }

    private CoinOfMastery(final CoinOfMastery card) {
        super(card);
    }

    @Override
    public CoinOfMastery copy() {
        return new CoinOfMastery(this);
    }
}

class CoinOfMasteryEffect extends ReplacementEffectImpl {

    CoinOfMasteryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "each creature you control enters with an additional " +
            "+1/+1 counter on it for each mana from an artifact source spent to cast it";
    }

    private CoinOfMasteryEffect(final CoinOfMasteryEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && !creature.getId().equals(source.getSourceId())
                && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        int manaPaid = ManaPaidSourceWatcher.getArtifactPaid(creature.getId(), game);
        if (manaPaid < 1) {
            return false;
        }
        creature.addCounters(
                CounterType.P1P1.createInstance(manaPaid),
                source.getControllerId(), source, game, event.getAppliedEffects()
        );
        return false;
    }

    @Override
    public CoinOfMasteryEffect copy() {
        return new CoinOfMasteryEffect(this);
    }
}
