/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 * @author LevelX2
 */
public class BecomesSubtypeAllEffect extends ContinuousEffectImpl {

    protected SubTypeList subtypes = new SubTypeList();
    protected boolean loseOther; // loses other subtypes
    protected FilterCreaturePermanent filter;

    public BecomesSubtypeAllEffect(Duration duration, SubType subtype) {
        this(duration, createArrayList(subtype));
    }

    public BecomesSubtypeAllEffect(Duration duration, SubTypeList subtypes) {
        this(duration, subtypes, new FilterCreaturePermanent("All creatures"), true);
    }

    public BecomesSubtypeAllEffect(Duration duration,
                                   SubTypeList subtypes, FilterCreaturePermanent filter, boolean loseOther) {
        super(duration, Outcome.Detriment);
        this.subtypes = subtypes;
        this.staticText = setText();
        this.loseOther = loseOther;
        this.filter = filter;
    }

    private static SubTypeList createArrayList(SubType subtype) {
        SubTypeList subtypes = new SubTypeList();
        subtypes.add(subtype);
        return subtypes;
    }

    public BecomesSubtypeAllEffect(final BecomesSubtypeAllEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
        this.loseOther = effect.loseOther;
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesSubtypeAllEffect copy() {
        return new BecomesSubtypeAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source,
                         Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (loseOther) {
                            permanent.getSubtype(game).retainAll(SubType.getLandTypes());
                            permanent.getSubtype(game).addAll(subtypes);
                        } else {
                            for (SubType subtype : subtypes) {
                                if (!permanent.hasSubtype(subtype, game)) {
                                    permanent.getSubtype(game).add(subtype);
                                }
                            }
                        }
                        break;
                }
            } else if (duration == Duration.Custom) {
                discard();
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Target creature becomes that type");
        if (!duration.toString().isEmpty()
                && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
