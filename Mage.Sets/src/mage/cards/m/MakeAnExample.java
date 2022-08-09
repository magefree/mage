package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class MakeAnExample extends CardImpl {

    public MakeAnExample(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Each opponent separates the creatures they control into two piles. For each opponent, you choose one of their piles. Each opponent sacrifices the creatures in their chosen pile.
        this.getSpellAbility().addEffect(new MakeAnExampleEffect());
    }

    private MakeAnExample(final MakeAnExample card) {
        super(card);
    }

    @Override
    public MakeAnExample copy() {
        return new MakeAnExample(this);
    }
}

class MakeAnExampleEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures to put in the first pile");

    public MakeAnExampleEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent separates the creatures they control into two piles. For each opponent, you choose one of their piles. Each opponent sacrifices the creatures in their chosen pile";
    }

    private MakeAnExampleEffect(final MakeAnExampleEffect effect) {
        super(effect);
    }

    @Override
    public MakeAnExampleEffect copy() {
        return new MakeAnExampleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        List<Permanent> toSacrifice = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(controllerId)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
            opponent.chooseTarget(Outcome.Sacrifice, target, source, game);
            List<UUID> chosenTargets = target.getTargets();
            List<Permanent> pile1 = new ArrayList<>();
            List<Permanent> pile2 = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, opponentId, game)) {
                if (chosenTargets.contains(permanent.getId())) {
                    pile1.add(permanent);
                } else {
                    pile2.add(permanent);
                }
            }
            if (controller.choosePile(Outcome.Sacrifice, "Choose a pile for " + opponent.getName() + " to sacrifice", pile1, pile2, game)) {
                toSacrifice.addAll(pile1);
            } else {
                toSacrifice.addAll(pile2);
            }
        }
        for (Permanent permanent : toSacrifice) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
