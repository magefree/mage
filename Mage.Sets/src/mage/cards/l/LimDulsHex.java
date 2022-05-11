package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class LimDulsHex extends CardImpl {

    public LimDulsHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, for each player, Lim-Dul's Hex deals 1 damage to that player unless they pay {B} or {3}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LimDulsHexEffect(), TargetController.YOU, false));
    }

    private LimDulsHex(final LimDulsHex card) {
        super(card);
    }

    @Override
    public LimDulsHex copy() {
        return new LimDulsHex(this);
    }
}

class LimDulsHexEffect extends OneShotEffect {

    public LimDulsHexEffect() {
        super(Outcome.Damage);
        this.staticText = "for each player, {this} deals 1 damage to that player unless they pay {B} or {3}";
    }

    public LimDulsHexEffect(final LimDulsHexEffect effect) {
        super(effect);
    }

    @Override
    public LimDulsHexEffect copy() {
        return new LimDulsHexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    OrCost costToPay = new OrCost("{B} or {3}", new ManaCostsImpl("{B}"), new ManaCostsImpl("{3}"));
                    costToPay.clearPaid();
                    if (!(player.chooseUse(Outcome.Benefit, "Pay {B} or {3}?", source, game) && costToPay.pay(source, game, source, player.getId(), false, null))) {
                        game.informPlayers(player.getLogName() + " chooses not to pay " + costToPay.getText() + " to prevent 1 damage from " + sourcePermanent.getLogName());
                        player.damage(1, sourcePermanent.getId(), source, game);
                    } else {
                        game.informPlayers(player.getLogName() + " chooses to pay " + costToPay.getText() + " to prevent 1 damage from " + sourcePermanent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
