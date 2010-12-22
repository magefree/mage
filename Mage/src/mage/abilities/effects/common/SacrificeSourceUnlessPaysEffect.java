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
    }

    public SacrificeSourceUnlessPaysEffect(final SacrificeSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
		if (player != null && player.chooseUse(Outcome.Benefit, cost.getText() + " or sacrifice {this}?", game)) {
			cost.clearPaid();
			if (cost.pay(game, source.getId(), source.getControllerId(), false))
				return true;
		}
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null)
			permanent.sacrifice(source.getSourceId(), game);
		return true;
    }

    @Override
    public SacrificeSourceUnlessPaysEffect copy() {
        return new SacrificeSourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "sacrifice it unless you pay " + cost.getText();
    }
}
