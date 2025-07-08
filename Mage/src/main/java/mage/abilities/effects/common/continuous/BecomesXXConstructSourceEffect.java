package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;

public class BecomesXXConstructSourceEffect extends ContinuousEffectImpl {

    public BecomesXXConstructSourceEffect(Duration duration) {
        super(duration, Outcome.BecomeCreature);
        StringBuilder sb = new StringBuilder("{this} becomes an X/X Construct artifact creature");
        if (duration == Duration.EndOfTurn) {
            sb.append(" until end of turn");
        }
        this.staticText = sb.toString();
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private BecomesXXConstructSourceEffect(final BecomesXXConstructSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesXXConstructSourceEffect copy() {
        return new BecomesXXConstructSourceEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    if (!permanent.isArtifact(game)) {
                        permanent.addCardType(game, CardType.ARTIFACT);
                    }
                    if (!permanent.isCreature(game)) {
                        permanent.addCardType(game, CardType.CREATURE);
                    }
                    permanent.removeAllCreatureTypes(game);
                    permanent.addSubType(game, SubType.CONSTRUCT);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
                        permanent.getPower().setModifiedBaseValue(xValue);
                        permanent.getToughness().setModifiedBaseValue(xValue);
                    }
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
