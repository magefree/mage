package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BoostOpponentsEffect extends ContinuousEffectImpl {
    protected int power;
    protected int toughness;
    protected FilterCreaturePermanent filter;

    public BoostOpponentsEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public BoostOpponentsEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, toughness < 0 ? Outcome.UnboostCreature : Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        setText();
    }

    protected BoostOpponentsEffect(final BoostOpponentsEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter.copy();
    }

    @Override
    public BoostOpponentsEffect copy() {
        return new BoostOpponentsEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (opponents.contains(perm.getControllerId())) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (getAffectedObjectsSet()) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still gets boost
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    if (opponents.contains(permanent.getControllerId())) {
                        affectedObjects.add(permanent);
                    }
                } else {
                    it.remove();
                }
            }
        } else {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (opponents.contains(permanent.getControllerId())) {
                    affectedObjects.add(permanent);
                }
            }
        }
        if (affectedObjects.isEmpty()) {
            if (getAffectedObjectsSet()) {
                this.discard();
            }
            return false;
        }
        return true;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(power);
            permanent.addToughness(toughness);
        }
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        sb.append(" your opponents control get ").append(CardUtil.getBoostCountAsStr(power, toughness));
        sb.append((duration == Duration.EndOfTurn ? " until end of turn" : ""));
        staticText = sb.toString();
    }
}
