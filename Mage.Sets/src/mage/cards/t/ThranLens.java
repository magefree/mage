
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class ThranLens extends CardImpl {

    public ThranLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        // All permanents are colorless.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThranLensEffect()));
    }

    private ThranLens(final ThranLens card) {
        super(card);
    }

    @Override
    public ThranLens copy() {
        return new ThranLens(this);
    }
    
   
}
 class ThranLensEffect extends ContinuousEffectImpl {

    public ThranLensEffect()
    {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "All permanents are colorless";
    }
    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm: game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            perm.getColor(game).setColor(ObjectColor.COLORLESS);
        }
        return true;
    }

    @Override
    public ThranLensEffect copy() {
        return new ThranLensEffect(this);
    }

    private ThranLensEffect(ThranLensEffect effect) {
        super(effect);
    }


}
