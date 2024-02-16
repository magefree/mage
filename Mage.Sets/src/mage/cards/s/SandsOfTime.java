
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipUntapStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class SandsOfTime extends CardImpl {

    public SandsOfTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Each player skips their untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapStepEffect()));

        // At the beginning of each player's upkeep, that player simultaneously untaps each tapped artifact, creature, and land they control and taps each untapped artifact, creature, and land they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SandsOfTimeEffect(), TargetController.ANY, false));
    }

    private SandsOfTime(final SandsOfTime card) {
        super(card);
    }

    @Override
    public SandsOfTime copy() {
        return new SandsOfTime(this);
    }
}

class SandsOfTimeEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }
    
    public SandsOfTimeEffect() {
        super(Outcome.Neutral);
        staticText = "that player simultaneously untaps each tapped artifact, creature, and land they control and taps each untapped artifact, creature, and land they control";
    }

    private SandsOfTimeEffect(final SandsOfTimeEffect copy) {
        super(copy);
    }

    @Override
    public SandsOfTimeEffect copy() {
        return new SandsOfTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game)) {
                if (permanent.isTapped()) {
                    permanent.untap(game);
                } else {
                    permanent.tap(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
