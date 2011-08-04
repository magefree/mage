package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * Created by IntelliJ IDEA.
 * User: Loki
 * Date: 21.12.10
 * Time: 9:21
 */
public class SacrificeSourceUnlessPaysEffect extends OneShotEffect<SacrificeSourceUnlessPaysEffect> {
    protected Cost cost;

    public SacrificeSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        staticText = "sacrifice {this} unless you pay " + cost.getText();
    }

    public SacrificeSourceUnlessPaysEffect(final SacrificeSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (player != null && permanent != null) { 
			if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() /* + " or sacrifice " + permanent.getName() */ + "?", game)) {
				cost.clearPaid();
				if (cost.pay(source, game, source.getId(), source.getControllerId(), false))
					return true;
			}
			permanent.sacrifice(source.getSourceId(), game);
			return true;
		}
		return false;
    }

    @Override
    public SacrificeSourceUnlessPaysEffect copy() {
        return new SacrificeSourceUnlessPaysEffect(this);
    }

 }
