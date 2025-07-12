package mage.cards.b;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladeOfTheOni extends CardImpl {

    public BladeOfTheOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Equipped creature has base power and toughness 5/5, has menace, and is a black Demon in addition to its other colors and types.
        this.addAbility(new SimpleStaticAbility(new BladeOfTheOniEffect()));

        // Reconfigure {2}{B}{B}
        this.addAbility(new ReconfigureAbility("{2}{B}{B}"));
    }

    private BladeOfTheOni(final BladeOfTheOni card) {
        super(card);
    }

    @Override
    public BladeOfTheOni copy() {
        return new BladeOfTheOni(this);
    }
}

class BladeOfTheOniEffect extends ContinuousEffectImpl {

    BladeOfTheOniEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "equipped creature has base power and toughness 5/5, " +
                "has menace, and is a black Demon in addition to its other colors and types";
    }

    private BladeOfTheOniEffect(final BladeOfTheOniEffect effect) {
        super(effect);
    }

    @Override
    public BladeOfTheOniEffect copy() {
        return new BladeOfTheOniEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.DEMON);
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor(game).setBlack(true);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new MenaceAbility(false), source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(5);
                        permanent.getToughness().setModifiedBaseValue(5);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
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
        switch (layer) {
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
