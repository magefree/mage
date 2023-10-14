
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class RousingOfSouls extends CardImpl {

    public RousingOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Parley - Each player reveals the top card of their library. For each nonland card revealed this way,
        // you create a 1/1 white Spirit creature token with flying. Then each player draws a card.
        this.getSpellAbility().addEffect(new RousingOfSoulsEffect());
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        this.getSpellAbility().addEffect(effect);
    }

    private RousingOfSouls(final RousingOfSouls card) {
        super(card);
    }

    @Override
    public RousingOfSouls copy() {
        return new RousingOfSouls(this);
    }
}

class RousingOfSoulsEffect extends OneShotEffect {

    public RousingOfSoulsEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Parley</i> &mdash; Each player reveals the top card of their library. For each nonland card revealed this way, you create a 1/1 white Spirit creature token with flying";
    }

    private RousingOfSoulsEffect(final RousingOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public RousingOfSoulsEffect copy() {
        return new RousingOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                new CreateTokenEffect(new SpiritWhiteToken(), parley).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
