
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrarksThumb extends CardImpl {

    public KrarksThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        addSuperType(SuperType.LEGENDARY);

        // If you would flip a coin, instead flip two coins and ignore one.
        this.addAbility(new SimpleStaticAbility(new KrarksThumbEffect()));
    }

    private KrarksThumb(final KrarksThumb card) {
        super(card);
    }

    @Override
    public KrarksThumb copy() {
        return new KrarksThumb(this);
    }
}

class KrarksThumbEffect extends ContinuousEffectImpl {

    KrarksThumbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would flip a coin, instead flip two coins and ignore one.";
    }

    private KrarksThumbEffect(final KrarksThumbEffect effect) {
        super(effect);
    }

    @Override
    public KrarksThumbEffect copy() {
        return new KrarksThumbEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setExtraCoinFlips(2 * controller.getExtraCoinFlips());
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
