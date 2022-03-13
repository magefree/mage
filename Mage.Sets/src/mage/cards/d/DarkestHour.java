
package mage.cards.d;

import java.util.UUID;
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

/**
 *
 * @author emerald000
 */
public final class DarkestHour extends CardImpl {

    public DarkestHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // All creatures are black.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DarkestHourEffect()));
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

    DarkestHourEffect(final DarkestHourEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
            permanent.getColor(game).setColor(ObjectColor.BLACK);
        }
        return true;
    }

    @Override
    public DarkestHourEffect copy() {
        return new DarkestHourEffect(this);
    }
}
