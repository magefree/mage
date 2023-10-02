
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 * @author nantuko
 */
public final class TimelyReinforcements extends CardImpl {

    public TimelyReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");


        // If you have less life than an opponent, you gain 6 life. If you control fewer creatures than an opponent, create three 1/1 white Soldier creature tokens.
        this.getSpellAbility().addEffect(new TimelyReinforcementsEffect());
    }

    private TimelyReinforcements(final TimelyReinforcements card) {
        super(card);
    }

    @Override
    public TimelyReinforcements copy() {
        return new TimelyReinforcements(this);
    }
}

class TimelyReinforcementsEffect extends OneShotEffect {

    public TimelyReinforcementsEffect() {
        super(Outcome.Benefit);
        staticText = "If you have less life than an opponent, you gain 6 life. If you control fewer creatures than an opponent, create three 1/1 white Soldier creature tokens";
    }

    private TimelyReinforcementsEffect(final TimelyReinforcementsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean lessCreatures = false;
            boolean lessLife = false;
            FilterPermanent filter= new FilterCreaturePermanent();
            int count = game.getBattlefield().countAll(filter, controller.getId(), game);
            for (UUID uuid : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(uuid);
                if (opponent != null) {
                    if (opponent.getLife() > controller.getLife()) {
                         lessLife = true;
                    }
                    if (game.getBattlefield().countAll(filter, uuid, game) > count) {
                        lessCreatures = true;
                    }
                }
                if ( lessLife && lessCreatures) { // no need to search further
                    break;
                }
            }
            if (lessLife) {
                controller.gainLife(6, game, source);
            }
            if (lessCreatures) {
                Effect effect = new CreateTokenEffect(new SoldierToken(), 3);
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public TimelyReinforcementsEffect copy() {
        return new TimelyReinforcementsEffect(this);
    }
}
