
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class DashHopes extends CardImpl {

    public DashHopes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");

        // When you cast Dash Hopes, any player may pay 5 life. If a player does, counter Dash Hopes.
        this.addAbility(new CastSourceTriggeredAbility(new DashHopesCounterSourceEffect()));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DashHopes(final DashHopes card) {
        super(card);
    }

    @Override
    public DashHopes copy() {
        return new DashHopes(this);
    }
}

class DashHopesCounterSourceEffect extends OneShotEffect {

    public DashHopesCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may pay 5 life. If a player does, counter {this}";
    }

    private DashHopesCounterSourceEffect(final DashHopesCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public DashHopesCounterSourceEffect copy() {
        return new DashHopesCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            PayLifeCost cost = new PayLifeCost(5);
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if(player != null) {
                    cost.clearPaid();
                    if (cost.canPay(source, source, player.getId(), game)
                            && player.chooseUse(outcome, "Pay 5 life to counter " + sourceObject.getIdName() + '?', source, game)) {
                        if (cost.pay(source, game, source, player.getId(), false, null)) {
                            game.informPlayers(player.getLogName() + " pays 5 life to counter " + sourceObject.getIdName() + '.');
                            Spell spell = game.getStack().getSpell(source.getSourceId());
                            if (spell != null) {
                                game.getStack().counter(spell.getId(), source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
