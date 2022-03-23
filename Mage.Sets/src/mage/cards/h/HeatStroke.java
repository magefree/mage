
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedThisTurnWatcher;
import mage.watchers.common.WasBlockedThisTurnWatcher;

/**
 * @author dustinroepsch
 */
public final class HeatStroke extends CardImpl {

    public HeatStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At end of combat, destroy each creature that blocked or was blocked this turn.
        Ability ability = new EndOfCombatTriggeredAbility(new HeatStrokeEffect(), false);
        ability.addWatcher(new BlockedThisTurnWatcher());
        ability.addWatcher(new WasBlockedThisTurnWatcher());
        this.addAbility(ability);
    }

    private HeatStroke(final HeatStroke card) {
        super(card);
    }

    @Override
    public HeatStroke copy() {
        return new HeatStroke(this);
    }
}

class HeatStrokeEffect extends OneShotEffect {

    public HeatStrokeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy each creature that blocked or was blocked this turn";
    }

    public HeatStrokeEffect(HeatStrokeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        BlockedThisTurnWatcher blockedWatcher = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        WasBlockedThisTurnWatcher wasBlockedThisTurnWatcher = game.getState().getWatcher(WasBlockedThisTurnWatcher.class);

        Set<Permanent> inROI = new HashSet<>(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game));
        boolean toRet = false;
        Set<MageObjectReference> toDestroy = new HashSet<>();

        if (blockedWatcher != null){
            toDestroy.addAll(blockedWatcher.getBlockedThisTurnCreatures());
        }
        if (wasBlockedThisTurnWatcher != null){
            toDestroy.addAll(wasBlockedThisTurnWatcher.getWasBlockedThisTurnCreatures());
        }

        for (MageObjectReference mor : toDestroy) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null && permanent.isCreature(game) && inROI.contains(permanent)){
                permanent.destroy(source, game, false);
                toRet = true;
            }
        }
        return toRet;
    }

    @Override
    public HeatStrokeEffect copy() {
        return new HeatStrokeEffect(this);
    }
}