package mage.cards.d;

import java.util.*;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DemonicJunkerEffect())
                .setTriggerPhrase("When this Vehicle enters, "));

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
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        List<Permanent> chosenCreatures = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
            Player currPlayer = game.getPlayer(playerId);
            if (currPlayer == null) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature controlled by " + currPlayer.getName());
            filter.add(new ControllerIdPredicate(playerId));
            TargetPermanent target = new TargetPermanent(0, 1, filter, true);
            controller.chooseTarget(Outcome.DestroyPermanent, target, source, game);
            Permanent choice = game.getPermanent(target.getFirstTarget());
            if (choice != null) {
                chosenCreatures.add(choice);
            }
        }
        List<Permanent> destroyedCreatures = new ArrayList<>();
        for (Permanent creature : chosenCreatures) {
            if (creature.destroy(source, game, false)) {
                destroyedCreatures.add(creature);
            }
        }
        for (Permanent destroyedCreature : destroyedCreatures) {
            if (destroyedCreature.getControllerId().equals(controllerId)) {
                Permanent vehicle = game.getPermanent(source.getSourceId());
                if (vehicle != null) {
                    vehicle.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
                }
            }
        }
        return true;
    }
}