
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public MeltingEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
    }

    public MeltingEffect(final MeltingEffect effect) {
        super(effect);
    }

    @Override
    public MeltingEffect copy() {
        return new MeltingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.getSuperType().remove(SuperType.SNOW);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "All lands are no longer snow";
    }
}
