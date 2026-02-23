package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class YouDontLoseManaEffect extends ContinuousEffectImpl {

    private final ManaType manaType;

    public YouDontLoseManaEffect(ManaType manaType) {
        this(Duration.WhileOnBattlefield, manaType);
    }

    public YouDontLoseManaEffect(Duration duration, ManaType manaType) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = (duration == Duration.EndOfTurn ? "until end of turn, " : "") +
                "you don't lose unspent " + manaType + " mana as steps and phases end";
        this.manaType = manaType;
    }

    private YouDontLoseManaEffect(final YouDontLoseManaEffect effect) {
        super(effect);
        this.manaType = effect.manaType;
    }

    @Override
    public YouDontLoseManaEffect copy() {
        return new YouDontLoseManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addDoNotEmptyManaType(manaType);
        }
        return false;
    }
}
