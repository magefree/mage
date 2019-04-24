
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Alexsandr0x
 */
public final class CitadelOfPain extends CardImpl {

    public CitadelOfPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE,
                "beginning of the end step", true,
                new CitadelOfPainEffect());
        // At the beginning of each player's end step, Citadel of Pain deals X damage to that player, where X is the number of untapped lands he or she controls.
        this.addAbility(triggered);
    }

    public CitadelOfPain(final CitadelOfPain card) {
        super(card);
    }

    @Override
    public CitadelOfPain copy() {
        return new CitadelOfPain(this);
    }
}

class CitadelOfPainEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    @Override
    public String getText(Mode mode) {
        return "{this} deals X damage to that player, where X is the number of untapped lands he or she controls.";
    }

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public CitadelOfPainEffect() {
        super(Outcome.Damage);
    }

    public CitadelOfPainEffect(Outcome outcome) {
        super(outcome);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int damage = game.getBattlefield().countAll(filter, game.getActivePlayerId(), game);
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new CitadelOfPainEffect();
    }
}