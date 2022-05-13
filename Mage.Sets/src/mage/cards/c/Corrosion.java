
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class Corrosion extends CardImpl {

    public Corrosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{R}");

        // Cumulative upkeep-Pay {1}.
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(1)));

        // At the beginning of your upkeep, put a rust counter on each artifact target opponent controls. Then destroy each artifact with converted mana cost less than or equal to the number of rust counters on it. Artifacts destroyed this way can't be regenerated.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CorrosionUpkeepEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
        // When Corrosion leaves the battlefield, remove all rust counters from all permanents.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CorrosionRemoveCountersEffect(), false)); 
    }

    private Corrosion(final Corrosion card) {
        super(card);
    }

    @Override
    public Corrosion copy() {
        return new Corrosion(this);
    }
}

class CorrosionUpkeepEffect extends OneShotEffect {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();
    
    CorrosionUpkeepEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "put a rust counter on each artifact target opponent controls. Then destroy each artifact with mana value less than or equal to the number of rust counters on it. Artifacts destroyed this way can't be regenerated";
    }
    
    CorrosionUpkeepEffect(final CorrosionUpkeepEffect effect) {
        super(effect);
    }
    
    @Override
    public CorrosionUpkeepEffect copy() {
        return new CorrosionUpkeepEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            // put a rust counter on each artifact target opponent controls
            if (targetPlayer != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, targetPlayer.getId(), game)) {
                    permanent.addCounters(CounterType.RUST.createInstance(), source.getControllerId(), source, game);
                }
            }
            // destroy each artifact with converted mana cost less than or equal to the number of rust counters on it
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.getManaValue() <= permanent.getCounters(game).getCount(CounterType.RUST)) {
                    permanent.destroy(source, game, true);
                }
            }
            return true;
        }
        return false;
    }
}

class CorrosionRemoveCountersEffect extends OneShotEffect {

    public CorrosionRemoveCountersEffect() {
        super(Outcome.Neutral);
        staticText = "remove all rust counters from all permanents";
    }

    public CorrosionRemoveCountersEffect(final CorrosionRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public CorrosionRemoveCountersEffect copy() {
        return new CorrosionRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            permanent.removeCounters(CounterType.RUST.createInstance(permanent.getCounters(game).getCount(CounterType.RUST)), source, game);
        }
        return true;
    }
}
