package mage.cards.d;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class DemonicJunker extends CardImpl {

    public DemonicJunker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{B}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // When this Vehicle enters, for each player, destroy up to one target creature that player controls. If a creature you controlled was destroyed this way, put two +1/+1 counters on this Vehicle.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DemonicJunkerEffect()
                .setTargetPointer(new EachTargetPointer()))
                .setTriggerPhrase("When this Vehicle enters, ");
        this.addAbility(ability.setTargetAdjuster(DemonicJunkerAdjuster.instance));

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private DemonicJunker(final DemonicJunker card) {
        super(card);
    }

    @Override
    public DemonicJunker copy() {
        return new DemonicJunker(this);
    }
}

enum DemonicJunkerAdjuster implements TargetAdjuster {
    instance;
    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            String playerName = ability.isControlledBy(playerId) ? "you" : player.getName();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature controlled by " + playerName);
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class DemonicJunkerEffect extends OneShotEffect {

    DemonicJunkerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "for each player, destroy up to one target creature that player controls. If a creature you controlled was destroyed this way, put two +1/+1 counters on this Vehicle";
    }

    public DemonicJunkerEffect(final DemonicJunkerEffect effect) {
        super(effect);
    }

    @Override
    public DemonicJunkerEffect copy() {
        return new DemonicJunkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean giveCounters = false;
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            if (permanent.destroy(source, game, false)) {
                giveCounters = permanent.getControllerId().equals(source.getControllerId());
            }
            if (giveCounters) {
                Permanent vehicle = game.getPermanent(source.getSourceId());
                if (vehicle != null) {
                    vehicle.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                }
            }
        }
        return true;
    }
}