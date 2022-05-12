package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Humiliate extends CardImpl {

    public Humiliate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card. Put a +1/+1 counter on a creature you control.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.OPPONENT
        ));
        this.getSpellAbility().addEffect(new HumiliateEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Humiliate(final Humiliate card) {
        super(card);
    }

    @Override
    public Humiliate copy() {
        return new Humiliate(this);
    }
}

class HumiliateEffect extends OneShotEffect {

    HumiliateEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on a creature you control";
    }

    private HumiliateEffect(final HumiliateEffect effect) {
        super(effect);
    }

    @Override
    public HumiliateEffect copy() {
        return new HumiliateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        target.withChooseHint("+1/+1 counter");
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(
                CounterType.P1P1.createInstance(), source.getControllerId(), source, game
        );
    }
}
