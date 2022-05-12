package mage.cards.w;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WojekSiren extends CardImpl {

    public WojekSiren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Radiance - Target creature and each other creature that shares a color with it get +1/+1 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new WojekSirenBoostEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);

    }

    private WojekSiren(final WojekSiren card) {
        super(card);
    }

    @Override
    public WojekSiren copy() {
        return new WojekSiren(this);
    }
}

class WojekSirenBoostEffect extends ContinuousEffectImpl {

    public WojekSirenBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Target creature and each other creature that shares a color with it get +1/+1 until end of turn";
    }

    public WojekSirenBoostEffect(final WojekSirenBoostEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            affectedObjectList.add(new MageObjectReference(target, game));
            ObjectColor color = target.getColor(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                if (!permanent.getId().equals(target.getId()) && permanent.getColor(game).shares(color)) {
                    affectedObjectList.add(new MageObjectReference(permanent, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mageObjectReference : affectedObjectList) {
            Permanent permanent = mageObjectReference.getPermanent(game);
            if (permanent != null) {
                permanent.addPower(1);
                permanent.addToughness(1);
            }
        }
        return true;
    }

    @Override
    public WojekSirenBoostEffect copy() {
        return new WojekSirenBoostEffect(this);
    }
}
