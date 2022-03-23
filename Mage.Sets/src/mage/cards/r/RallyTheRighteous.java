
package mage.cards.r;

import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetCreaturePermanent;

/**
 * @author duncant
 */
public final class RallyTheRighteous extends CardImpl {

    public RallyTheRighteous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");

        // Radiance — Untap target creature and each other creature that shares a color with it. Those creatures get +2/+0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new RallyTheRighteousUntapEffect());
        this.getSpellAbility().addEffect(new RallyTheRighteousBoostEffect());
    }

    private RallyTheRighteous(final RallyTheRighteous card) {
        super(card);
    }

    @Override
    public RallyTheRighteous copy() {
        return new RallyTheRighteous(this);
    }
}

class RallyTheRighteousUntapEffect extends OneShotEffect {

    public RallyTheRighteousUntapEffect() {
        super(Outcome.Untap);
        staticText = "<i>Radiance</i> &mdash; Untap target creature and each other creature that shares a color with it";
    }

    public RallyTheRighteousUntapEffect(final RallyTheRighteousUntapEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheRighteousUntapEffect copy() {
        return new RallyTheRighteousUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor(game);
            target.untap(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                if (permanent.getColor(game).shares(color) && !permanent.getId().equals(target.getId())) {
                    permanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}

class RallyTheRighteousBoostEffect extends ContinuousEffectImpl {

    public RallyTheRighteousBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Those creatures get +2/+0 until end of turn";
    }

    public RallyTheRighteousBoostEffect(final RallyTheRighteousBoostEffect effect) {
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
                permanent.addPower(2);
            }
        }
        return true;
    }

    @Override
    public RallyTheRighteousBoostEffect copy() {
        return new RallyTheRighteousBoostEffect(this);
    }
}
