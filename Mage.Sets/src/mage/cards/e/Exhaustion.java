
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Exhaustion extends CardImpl {

    public Exhaustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Creatures and lands target opponent controls don't untap during their next untap step.
        this.getSpellAbility().addEffect(new ExhaustionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Exhaustion(final Exhaustion card) {
        super(card);
    }

    @Override
    public Exhaustion copy() {
        return new Exhaustion(this);
    }
}

class ExhaustionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    ExhaustionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Creatures and lands target opponent controls don't untap during their next untap step.";
    }

    ExhaustionEffect(final ExhaustionEffect effect) {
        super(effect);
    }

    @Override
    public ExhaustionEffect copy() {
        return new ExhaustionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
   
        if (player != null) {
            ContinuousEffect effect = new DontUntapInPlayersNextUntapStepAllEffect(filter);
            effect.setTargetPointer(new FixedTarget(player.getId()));
            game.addEffect(effect, source);                      
            return true;
        }
        return false;
    }
}
