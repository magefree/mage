package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Melting extends CardImpl {

    public Melting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // All lands are no longer snow.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeltingEffect()));
    }

    private Melting(final Melting card) {
        super(card);
    }

    @Override
    public Melting copy() {
        return new Melting(this);
    }
}

class MeltingEffect extends ContinuousEffectImpl {

    MeltingEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "All lands are no longer snow";
    }

    private MeltingEffect(final MeltingEffect effect) {
        super(effect);
    }

    @Override
    public MeltingEffect copy() {
        return new MeltingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game)) {
            permanent.removeSuperType(game, SuperType.SNOW);
        }
        return true;
    }
}
