package mage.cards.i;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class InvokeDespair extends CardImpl {

    public InvokeDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}{B}{B}");

        // Target opponent sacrifices a creature. If they can't, they lose 2 life and you draw a card. Then repeat this process for an enchantment and a planeswalker.
        this.getSpellAbility().addEffect(new InvokeDespairEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private InvokeDespair(final InvokeDespair card) {
        super(card);
    }

    @Override
    public InvokeDespair copy() {
        return new InvokeDespair(this);
    }
}

class InvokeDespairEffect extends OneShotEffect {

    private static final FilterControlledEnchantmentPermanent enchantmentFilter = new FilterControlledEnchantmentPermanent();

    public InvokeDespairEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target opponent sacrifices a creature. If they can't, they lose 2 life and you draw a card. Then repeat this process for an enchantment and a planeswalker";
    }

    private InvokeDespairEffect(final InvokeDespairEffect effect) {
        super(effect);
    }

    @Override
    public InvokeDespairEffect copy() {
        return new InvokeDespairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        opponent.choose(outcome, target, source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        boolean sacrificed = false;
        if (permanent != null) {
            sacrificed = permanent.sacrifice(source, game);
        }
        if (!sacrificed) {
            opponent.loseLife(2, game, source, false);
            controller.drawCards(1, source, game);
        }
        target = new TargetControlledPermanent(enchantmentFilter);
        target.setNotTarget(true);
        opponent.choose(outcome, target, source.getSourceId(), game);
        permanent = game.getPermanent(target.getFirstTarget());
        sacrificed = false;
        if (permanent != null) {
            sacrificed = permanent.sacrifice(source, game);
        }
        if (!sacrificed) {
            opponent.loseLife(2, game, source, false);
            controller.drawCards(1, source, game);
        }
        target = new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER);
        target.setNotTarget(true);
        opponent.choose(outcome, target, source.getSourceId(), game);
        permanent = game.getPermanent(target.getFirstTarget());
        sacrificed = false;
        if (permanent != null) {
            sacrificed = permanent.sacrifice(source, game);
        }
        if (!sacrificed) {
            opponent.loseLife(2, game, source, false);
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
