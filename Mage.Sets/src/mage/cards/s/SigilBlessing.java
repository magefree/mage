
package mage.cards.s;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SigilBlessing extends CardImpl {

    public SigilBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");

        // Until end of turn, target creature you control gets +3/+3 and other creatures you control get +1/+1.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new SigilBlessingBoostControlledEffect());
    }

    private SigilBlessing(final SigilBlessing card) {
        super(card);
    }

    @Override
    public SigilBlessing copy() {
        return new SigilBlessing(this);
    }
}

class SigilBlessingBoostControlledEffect extends ContinuousEffectImpl {

    public SigilBlessingBoostControlledEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Until end of turn, target creature you control gets +3/+3 and other creatures you control get +1/+1";
    }

    private SigilBlessingBoostControlledEffect(final SigilBlessingBoostControlledEffect effect) {
        super(effect);
    }

    @Override
    public SigilBlessingBoostControlledEffect copy() {
        return new SigilBlessingBoostControlledEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                int boost = 1;
                if (permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
                    boost = 3;
                }
                permanent.addPower(boost);
                permanent.addToughness(boost);
            } else {
                it.remove(); // no longer on the battlefield, remove reference to object
            }
        }
        return true;
    }

}
