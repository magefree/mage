/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class BecomesCreatureTypeTargetEffect extends ContinuousEffectImpl {

    protected SubTypeList subtypes = new SubTypeList();
    protected boolean loseOther;  // loses other creature types

    public BecomesCreatureTypeTargetEffect(Duration duration, SubType subtype) {
        this(duration, createArrayList(subtype));
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, SubType subtype, boolean loseOther) {
        this(duration, createArrayList(subtype), loseOther);
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, SubTypeList subtypes) {
        this(duration, subtypes, true);
    }

    public BecomesCreatureTypeTargetEffect(Duration duration, SubTypeList subtypes, boolean loseOther) {
        super(duration, Outcome.Detriment);
        this.subtypes = subtypes;
        this.staticText = setText();
        this.loseOther = loseOther;
    }

    private static SubTypeList createArrayList(SubType subtype) {
        SubTypeList subtypes = new SubTypeList();
        subtypes.add(subtype);
        return subtypes;
    }

    public BecomesCreatureTypeTargetEffect(final BecomesCreatureTypeTargetEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
        this.loseOther = effect.loseOther;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesCreatureTypeTargetEffect copy() {
        return new BecomesCreatureTypeTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetPermanent);
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
            } else {
                if (duration == Duration.Custom) {
                    discard();
                }
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
        if (!duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
