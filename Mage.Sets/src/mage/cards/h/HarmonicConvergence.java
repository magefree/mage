
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.*;

/**
 *
 * @author North
 */
public final class HarmonicConvergence extends CardImpl {

    public HarmonicConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Put all enchantments on top of their owners' libraries.
        this.getSpellAbility().addEffect(new HarmonicConvergenceEffect());
    }

    public HarmonicConvergence(final HarmonicConvergence card) {
        super(card);
    }

    @Override
    public HarmonicConvergence copy() {
        return new HarmonicConvergence(this);
    }
}

class HarmonicConvergenceEffect extends OneShotEffect {

    public HarmonicConvergenceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put all enchantments on top of their owners' libraries";
    }

    public HarmonicConvergenceEffect(final HarmonicConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public HarmonicConvergenceEffect copy() {
        return new HarmonicConvergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> enchantments = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_ENCHANTMENT_PERMANENT,
                source.getControllerId(),
                source.getSourceId(),
                game);

        Map<UUID, List<Permanent>> moveList = new HashMap<>();
        for (Permanent permanent : enchantments) {
            List<Permanent> list = moveList.computeIfAbsent(permanent.getControllerId(), k -> new ArrayList<>());
            list.add(permanent);
        }

        TargetCard target = new TargetCard(Zone.BATTLEFIELD, new FilterCard("card to put on top of your library"));
        for (UUID playerId : moveList.keySet()) {
            Player player = game.getPlayer(playerId);
            List<Permanent> list = moveList.get(playerId);
            if (player == null) {
                continue;
            }

            CardsImpl cards = new CardsImpl();
            for (Permanent permanent : list) {
                cards.add(permanent);
            }
            while (player.canRespond() && cards.size() > 1) {
                player.choose(Outcome.Neutral, cards, target, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    cards.remove(permanent);
                    permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Permanent permanent = game.getPermanent(cards.iterator().next());
                permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }
        }

        return true;
    }
}
