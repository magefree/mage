
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class HallOfTriumph extends CardImpl {

    public HallOfTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.supertype.add(SuperType.LEGENDARY);

        // As Hall of Triumph enters the battlefield choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // Creatures you control of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HallOfTriumphBoostControlledEffect()));
    }

    private HallOfTriumph(final HallOfTriumph card) {
        super(card);
    }

    @Override
    public HallOfTriumph copy() {
        return new HallOfTriumph(this);
    }
}

class HallOfTriumphBoostControlledEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HallOfTriumphBoostControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen color get +1/+1";
    }

    public HallOfTriumphBoostControlledEffect(final HallOfTriumphBoostControlledEffect effect) {
        super(effect);
    }

    @Override
    public HallOfTriumphBoostControlledEffect copy() {
        return new HallOfTriumphBoostControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (perm.getColor(game).shares(color)) {
                    perm.addPower(1);
                    perm.addToughness(1);
                }
            }
            return true;
        }
        return false;
    }

}
