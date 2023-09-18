
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SetPlayerLifeAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class OnceMoreWithFeeling extends CardImpl {

    public OnceMoreWithFeeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{W}{W}");

        // Exile all permanents and all cards from all graveyards. Each player shuffles their hand into their library, then draws seven cards. Each player's life total becomes 10. Exile Once More with Feeling.
        this.getSpellAbility().addEffect(new OnceMoreWithFeelingEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new SetPlayerLifeAllEffect(10));
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // DCI ruling — A deck can have only one card named Once More with Feeling.
        // (according to rule 112.6m, this shouldn't do anything)
        this.getSpellAbility().addEffect(new InfoEffect("<br>DCI ruling &mdash; A deck can have only one card named {this}"));
    }

    private OnceMoreWithFeeling(final OnceMoreWithFeeling card) {
        super(card);
    }

    @Override
    public OnceMoreWithFeeling copy() {
        return new OnceMoreWithFeeling(this);
    }
}

class OnceMoreWithFeelingEffect extends OneShotEffect {

    public OnceMoreWithFeelingEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all permanents and all cards from all graveyards. Each player shuffles their hand into their library";
    }

    private OnceMoreWithFeelingEffect(final OnceMoreWithFeelingEffect effect) {
        super(effect);
    }

    @Override
    public OnceMoreWithFeelingEffect copy() {
        return new OnceMoreWithFeelingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            permanent.moveToExile(null, "", source, game);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source, game);
                    }
                }
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
