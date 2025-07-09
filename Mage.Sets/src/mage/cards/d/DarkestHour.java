
package mage.cards.d;

import mage.MageItem;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class DarkestHour extends CardImpl {

    public DarkestHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // All creatures are black.
        this.addAbility(new SimpleStaticAbility(new DarkestHourEffect()));
    }

    private DarkestHour(final DarkestHour card) {
        super(card);
    }

    @Override
    public DarkestHour copy() {
        return new DarkestHour(this);
    }
}

class DarkestHourEffect extends ContinuousEffectImpl {

    DarkestHourEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "All creatures are black";
    }

    private DarkestHourEffect(final DarkestHourEffect effect) {
        super(effect);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.getColor(game).setColor(ObjectColor.BLACK);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game));
        return !affectedObjects.isEmpty();
    }

    @Override
    public DarkestHourEffect copy() {
        return new DarkestHourEffect(this);
    }
}
