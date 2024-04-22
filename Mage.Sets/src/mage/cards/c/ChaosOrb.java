package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Heavily errated version of Chaos Orb, as it is not
 * realistic to implement any kind of fall simulation.
 * No dexterity, a single destroyed permanent at most,
 * and a randomized (not a dice roll) chance to destroy
 * the other permanent.
 *
 * @author Susucr
 */
public final class ChaosOrb extends CardImpl {

    public ChaosOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}: If Chaos Orb is on the battlefield, choose a nontoken permanent, then a number between 1 and 20 is generated at random. If the number is 4 or more, destroy the chosen permanent. Then destroy Chaos Orb.
        Ability ability = new SimpleActivatedAbility(
                new ChaosOrbEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DestroySourceEffect().concatBy("Then"));
        this.addAbility(ability);
    }

    private ChaosOrb(final ChaosOrb card) {
        super(card);
    }

    @Override
    public ChaosOrb copy() {
        return new ChaosOrb(this);
    }
}

class ChaosOrbEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    ChaosOrbEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "if {this} is on the battlefield, choose a nontoken permanent, "
                + "then a number between 1 and 20 is generated at random. "
                + "If the number is 4 or more, destroy the chosen permanent";
    }

    private ChaosOrbEffect(final ChaosOrbEffect effect) {
        super(effect);
    }

    @Override
    public ChaosOrbEffect copy() {
        return new ChaosOrbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent orb = source.getSourcePermanentIfItStillExists(game);
        if (player == null || orb == null) {
            return false;
        }

        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        Permanent chosen = null;
        if (target.choose(outcome, player.getId(), source.getSourceId(), source, game)) {
            chosen = game.getPermanent(target.getFirstTarget());
        }
        if (chosen != null) {
            game.informPlayers(player.getLogName() + " has chosen " + chosen.getLogName() + CardUtil.getSourceLogName(game, source));

            // roll a 20 side dice without any roll interaction.
            int result = player.rollDieResult(20, game);
            game.informPlayers("The random number is " + result + CardUtil.getSourceLogName(game, source));

            // 3/20 chance of failure.
            if (result >= 4) {
                chosen.destroy(source, game);
            }
        }

        return true;
    }

}