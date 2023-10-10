
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class TIEInterceptor extends CardImpl {

    public TIEInterceptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever TIE Interceptor attacks, each opponent loses 2 life.
        this.addAbility(new AttacksTriggeredAbility(new TIEInterceptorEffect(), false));
    }

    private TIEInterceptor(final TIEInterceptor card) {
        super(card);
    }

    @Override
    public TIEInterceptor copy() {
        return new TIEInterceptor(this);
    }
}

class TIEInterceptorEffect extends OneShotEffect {

    TIEInterceptorEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 2 life";
    }

    private TIEInterceptorEffect(final TIEInterceptorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(2, game, source, false);
            }
        }
        return true;
    }

    @Override
    public TIEInterceptorEffect copy() {
        return new TIEInterceptorEffect(this);

    }
}
