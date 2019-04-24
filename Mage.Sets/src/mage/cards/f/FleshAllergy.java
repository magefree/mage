
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author nantuko
 */
public final class FleshAllergy extends CardImpl {

    public FleshAllergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // As an additional cost to cast Flesh Allergy, sacrifice a creature.
        // Destroy target creature. Its controller loses life equal to the number of creatures that died this turn.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new FleshAllergyEffect());
        this.getSpellAbility().addWatcher(new FleshAllergyWatcher());
    }

    public FleshAllergy(final FleshAllergy card) {
        super(card);
    }

    @Override
    public FleshAllergy copy() {
        return new FleshAllergy(this);
    }
}

class FleshAllergyWatcher extends Watcher {

    public int creaturesDiedThisTurn = 0;

    public FleshAllergyWatcher() {
        super(FleshAllergyWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public FleshAllergyWatcher(final FleshAllergyWatcher watcher) {
        super(watcher);
    }

    @Override
    public FleshAllergyWatcher copy() {
        return new FleshAllergyWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.isCreature()) {
                creaturesDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesDiedThisTurn = 0;
    }

}

class FleshAllergyEffect extends OneShotEffect {

    public FleshAllergyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Its controller loses life equal to the number of creatures that died this turn";
    }

    public FleshAllergyEffect(final FleshAllergyEffect effect) {
        super(effect);
    }

    @Override
    public FleshAllergyEffect copy() {
        return new FleshAllergyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FleshAllergyWatcher watcher = (FleshAllergyWatcher) game.getState().getWatchers().get(FleshAllergyWatcher.class.getSimpleName());
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null && watcher != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                int amount = watcher.creaturesDiedThisTurn;
                if (amount > 0) {
                    player.loseLife(amount, game, false);
                    return true;
                }
            }
        }
        return false;
    }

}
