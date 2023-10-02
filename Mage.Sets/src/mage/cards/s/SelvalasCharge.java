
package mage.cards.s;

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
import mage.game.permanent.token.ElephantToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class SelvalasCharge extends CardImpl {

    public SelvalasCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");

        // Parley - Each player reveals the top card of their library. For each nonland card revealed this way, you create a 3/3 green Elephant creature token. Then each player draws a card.
        this.getSpellAbility().addEffect(new SelvalasChargeEffect());
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        this.getSpellAbility().addEffect(effect);
    }

    private SelvalasCharge(final SelvalasCharge card) {
        super(card);
    }

    @Override
    public SelvalasCharge copy() {
        return new SelvalasCharge(this);
    }
}

class SelvalasChargeEffect extends OneShotEffect {

    public SelvalasChargeEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Parley</i> &mdash; Each player reveals the top card of their library. For each nonland card revealed this way, you create a 3/3 green Elephant creature token";
    }

    private SelvalasChargeEffect(final SelvalasChargeEffect effect) {
        super(effect);
    }

    @Override
    public SelvalasChargeEffect copy() {
        return new SelvalasChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                new CreateTokenEffect(new ElephantToken(), parley).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
