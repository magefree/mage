
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class DiabolicRevelation extends CardImpl {

    public DiabolicRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{3}{B}{B}");


        // Search your library for up to X cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new DiabolicRevelationEffect());
    }

    public DiabolicRevelation(final DiabolicRevelation card) {
        super(card);
    }

    @Override
    public DiabolicRevelation copy() {
        return new DiabolicRevelation(this);
    }
}

class DiabolicRevelationEffect extends OneShotEffect {

    public DiabolicRevelationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X cards and put those cards into your hand. Then shuffle your library";
    }

    public DiabolicRevelationEffect(final DiabolicRevelationEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicRevelationEffect copy() {
        return new DiabolicRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = ManacostVariableValue.instance.calculate(game, source, this);
        TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard());

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.searchLibrary(target, source, game)) {
            for (UUID cardId : target.getTargets()) {
                Card card = player.getLibrary().remove(cardId, game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }

        player.shuffleLibrary(source, game);
        return true;
    }
}
