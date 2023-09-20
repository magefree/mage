
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author MarcoMarin
 */
public final class CursedRack extends CardImpl {

    public CursedRack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Cursed Rack enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        // The chosen player's maximum hand size is four.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CursedRackHandSizeEffect()));

    }

    private CursedRack(final CursedRack card) {
        super(card);
    }

    @Override
    public CursedRack copy() {
        return new CursedRack(this);
    }
}

class CursedRackHandSizeEffect extends ContinuousEffectImpl {

    public CursedRackHandSizeEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "The chosen player's maximum hand size is four";
    }

    private CursedRackHandSizeEffect(final CursedRackHandSizeEffect effect) {
        super(effect);
    }

    @Override
    public CursedRackHandSizeEffect copy() {
        return new CursedRackHandSizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        Player opponent = game.getPlayer(playerId);
        if (opponent != null) {
            opponent.setMaxHandSize(4);
            return true;
        }
        return false;
    }
}
