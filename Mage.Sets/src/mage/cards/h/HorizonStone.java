package mage.cards.h;

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
 * @author TheElk801
 */
public final class HorizonStone extends CardImpl {

    public HorizonStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // If you would lose unspent mana, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(new HorizonStoneEffect()));
    }

    private HorizonStone(final HorizonStone card) {
        super(card);
    }

    @Override
    public HorizonStone copy() {
        return new HorizonStone(this);
    }
}

class HorizonStoneEffect extends ContinuousEffectImpl {

    HorizonStoneEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "if you would lose unspent mana, that mana becomes colorless instead";
    }

    private HorizonStoneEffect(final HorizonStoneEffect effect) {
        super(effect);
    }

    @Override
    public HorizonStoneEffect copy() {
        return new HorizonStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().setManaBecomesColorless(true);
        }
        return true;
    }
}
