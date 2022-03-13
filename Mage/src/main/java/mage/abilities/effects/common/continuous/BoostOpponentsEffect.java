package mage.abilities.effects.common.continuous;

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

    public BoostOpponentsEffect(final BoostOpponentsEffect effect) {
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
        if (this.affectedObjectsSet) {
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (opponents.contains(perm.getControllerId())) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent perm = it.next().getPermanent(game);
                if (perm != null) {
                    if (opponents.contains(perm.getControllerId())) {
                        perm.addPower(power);
                        perm.addToughness(toughness);
                    }
                } else {
                    it.remove();
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (opponents.contains(perm.getControllerId())) {
                    perm.addPower(power);
                    perm.addToughness(toughness);
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        sb.append(" your opponents control get ").append(CardUtil.getBoostCountAsStr(power, toughness));
        sb.append((duration == Duration.EndOfTurn ? " until end of turn" : ""));
        staticText = sb.toString();
    }
}
