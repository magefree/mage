
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class TangleWire extends CardImpl {

    public TangleWire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Fading 4
        this.addAbility(new FadingAbility(4, this));
        // At the beginning of each player's upkeep, that player taps an untapped artifact, creature, or land they control for each fade counter on Tangle Wire.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new TangleWireEffect(), TargetController.ANY, false, true));
    }

    private TangleWire(final TangleWire card) {
        super(card);
    }

    @Override
    public TangleWire copy() {
        return new TangleWire(this);
    }
}
class TangleWireEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifact, creature, or land they control");
    static{
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    TangleWireEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player taps an untapped artifact, creature, or land they control for each fade counter on Tangle Wire";
    }

    private TangleWireEffect(final TangleWireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }

        int targetCount = game.getBattlefield().countAll(filter, player.getId(), game);
        int counterCount = permanent.getCounters(game).getCount(CounterType.FADE);
        int amount = Math.min(counterCount, targetCount);

        Target target = new TargetControlledPermanent(amount, amount, filter, true);
        target.withNotTarget(true);

        if (amount > 0 && player.chooseTarget(Outcome.Tap, target, source, game)) {
            boolean abilityApplied = false;

            for (UUID uuid : target.getTargets()) {
                Permanent selectedPermanent = game.getPermanent(uuid);
                if ( selectedPermanent != null ) {
                    abilityApplied |= selectedPermanent.tap(source, game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    @Override
    public TangleWireEffect copy() {
        return new TangleWireEffect(this);
    }
}