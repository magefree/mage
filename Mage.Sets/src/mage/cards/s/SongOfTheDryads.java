package mage.cards.s;

import mage.MageItem;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SongOfTheDryads extends CardImpl {

    public SongOfTheDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted permanent is a colorless Forest land.
        this.addAbility(new SimpleStaticAbility(new BecomesColorlessForestLandEffect()));
    }

    private SongOfTheDryads(final SongOfTheDryads card) {
        super(card);
    }

    @Override
    public SongOfTheDryads copy() {
        return new SongOfTheDryads(this);
    }
}

class BecomesColorlessForestLandEffect extends ContinuousEffectImpl {

    BecomesColorlessForestLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Enchanted permanent is a colorless Forest land";
        dependencyTypes.add(DependencyType.BecomeForest);
    }

    private BecomesColorlessForestLandEffect(final BecomesColorlessForestLandEffect effect) {
        super(effect);
    }

    @Override
    public BecomesColorlessForestLandEffect copy() {
        return new BecomesColorlessForestLandEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case ColorChangingEffects_5:
                    permanent.getColor(game).setColor(ObjectColor.COLORLESS);
                    break;
                case TypeChangingEffects_4:
                    permanent.removeAllCardTypes(game);
                    permanent.addCardType(game, CardType.LAND);
                    permanent.removeAllSubTypes(game);
                    permanent.addSubType(game, SubType.FOREST);
                    permanent.removeAllAbilities(source.getSourceId(), game);
                    permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    break;
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
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
        return layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }
}
