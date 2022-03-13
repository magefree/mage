
package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author brikr
 */
public final class DralnusCrusade extends CardImpl {

    public DralnusCrusade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // Goblin creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false)));

        // All Goblins are black and are Zombies in addition to their other creature types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DralnusCrusadeEffect()));
    }

    private DralnusCrusade(final DralnusCrusade card) {
        super(card);
    }

    @Override
    public DralnusCrusade copy() {
        return new DralnusCrusade(this);
    }
}

class DralnusCrusadeEffect extends ContinuousEffectImpl {

    public DralnusCrusadeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All Goblins are black and are Zombies in addition to their other creature types";
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, source.getControllerId(), source, game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.ZOMBIE);
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.BLACK);
                    break;
            }
        }
        return true;
    }

    public DralnusCrusadeEffect(final DralnusCrusadeEffect effect) {
        super(effect);
    }

    @Override
    public DralnusCrusadeEffect copy() {
        return new DralnusCrusadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.ColorChangingEffects_5;
    }
}
