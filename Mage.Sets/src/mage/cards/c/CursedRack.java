
package mage.cards.c;

import mage.MageItem;
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

import java.util.List;
import java.util.UUID;

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
        this.addAbility(new SimpleStaticAbility(new CursedRackHandSizeEffect()));

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

    CursedRackHandSizeEffect() {
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
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        for (MageItem object : affectedObjects) {
            ((Player) object).setMaxHandSize(4);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        Player opponent = game.getPlayer(playerId);
        if (opponent != null) {
            affectedObjects.add(opponent);
            return true;
        }
        return false;
    }
}
