package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavaStorm extends CardImpl {

    public LavaStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Lava Storm deals 2 damage to each attacking creature or Lava Storm deals 2 damage to each blocking creature.
        this.getSpellAbility().addEffect(new LavaStormEffect());
    }

    private LavaStorm(final LavaStorm card) {
        super(card);
    }

    @Override
    public LavaStorm copy() {
        return new LavaStorm(this);
    }
}

class LavaStormEffect extends OneShotEffect {
    private static final FilterPermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
        filter2.add(BlockingPredicate.instance);
    }

    LavaStormEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 2 damage to each attacking creature " +
                "or {this} deals 2 damage to each blocking creature.";
    }

    private LavaStormEffect(final LavaStormEffect effect) {
        super(effect);
    }

    @Override
    public LavaStormEffect copy() {
        return new LavaStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(
                Outcome.Benefit, "Deal 2 damage to all attacking creatures or all blocking creatures?",
                null, "Attackers", "Blockers", source, game)
        ) {
            return new DamageAllEffect(2, filter).apply(game, source);
        }
        return new DamageAllEffect(2, filter2).apply(game, source);
    }
}