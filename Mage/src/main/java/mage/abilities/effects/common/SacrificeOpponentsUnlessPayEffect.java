package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Eirkei
 */
public class SacrificeOpponentsUnlessPayEffect extends OneShotEffect {

    protected Cost cost;
    protected FilterPermanent filter;

    public SacrificeOpponentsUnlessPayEffect(int genericManaCost) {
        this(new GenericManaCost(genericManaCost), StaticFilters.FILTER_PERMANENT);
    }

    public SacrificeOpponentsUnlessPayEffect(Cost cost, FilterPermanent filter) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        this.filter = filter;
        this.staticText = "each opponent sacrifices " + CardUtil.addArticle(filter.getMessage()) + " unless they " + CardUtil.addCostVerb(cost.getText());
    }

    protected SacrificeOpponentsUnlessPayEffect(final SacrificeOpponentsUnlessPayEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }

        if (effect.filter != null) {
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public SacrificeOpponentsUnlessPayEffect copy() {
        return new SacrificeOpponentsUnlessPayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> permsToSacrifice = new HashSet<>();

        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            Cost costToPay = cost.copy();
            String costValueMessage = costToPay.getText();
            String message = ((costToPay instanceof ManaCost) ? "Pay " : "") + costValueMessage + '?';

            costToPay.clearPaid();
            if (!(player.chooseUse(Outcome.Benefit, message, source, game)
                    && costToPay.pay(source, game, source, player.getId(), false, null))) {
                game.informPlayers(player.getLogName() + " chooses not to pay " + costValueMessage + " to prevent the sacrifice effect");

                if (game.getBattlefield().count(TargetSacrifice.makeFilter(filter), player.getId(), source, game) > 0) {
                    TargetSacrifice target = new TargetSacrifice(1, filter);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                        permsToSacrifice.addAll(target.getTargets());
                    }
                }
            } else {
                game.informPlayers(player.getLogName() + " chooses to pay " + costValueMessage + " to prevent the sacrifice effect");
            }
        }

        for (UUID permID : permsToSacrifice) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
