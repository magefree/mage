package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class CallToTheVoid extends CardImpl {

    public CallToTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Each player secretly chooses a creature they control and a creature they don't control. Then those choices are revealed. Destroy each creature chosen this way.
        this.getSpellAbility().addEffect(new CallToTheVoidEffect());
    }

    private CallToTheVoid(final CallToTheVoid card) {
        super(card);
    }

    @Override
    public CallToTheVoid copy() {
        return new CallToTheVoid(this);
    }
}

class CallToTheVoidEffect extends OneShotEffect {

    CallToTheVoidEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly chooses a creature they control and a creature they don't control. " +
                "Then those choices are revealed. Destroy each creature chosen this way";
    }

    private CallToTheVoidEffect(final CallToTheVoidEffect effect) {
        super(effect);
    }

    @Override
    public CallToTheVoidEffect copy() {
        return new CallToTheVoidEffect(this);
    }

    private void chooseCreature(Map<String, List<String>> map, Set<Permanent> permanents, FilterPermanent filter, Player player, Ability source, Game game) {
        if (game.getBattlefield().count(filter, player.getId(), source, game) > 0) {
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                map.computeIfAbsent(player.getLogName(), x -> new ArrayList<>()).add(permanent.getLogName());
                permanents.add(permanent);
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<String, List<String>> map = new HashMap<>();
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                chooseCreature(map, permanents, StaticFilters.FILTER_CONTROLLED_CREATURE, player, source, game);
                chooseCreature(map, permanents, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, player, source, game);
            }
        }
        map.entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> entry.getKey() + " has chosen " + CardUtil.concatWithAnd(entry.getValue()))
                .forEach(game::informPlayers);
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
