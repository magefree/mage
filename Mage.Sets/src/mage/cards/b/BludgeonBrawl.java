
package mage.cards.b;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class BludgeonBrawl extends CardImpl {

    public BludgeonBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Each noncreature, non-Equipment artifact is an Equipment with equip {X} and "Equipped creature gets +X/+0," where X is that artifact's converted mana cost.
        this.addAbility(new SimpleStaticAbility(new BludgeonBrawlEffect()));
    }

    private BludgeonBrawl(final BludgeonBrawl card) {
        super(card);
    }

    @Override
    public BludgeonBrawl copy() {
        return new BludgeonBrawl(this);
    }
}

class BludgeonBrawlEffect extends ContinuousEffectImpl {
    private static final FilterPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(SubType.EQUIPMENT.getPredicate()));
    }

    public BludgeonBrawlEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "each noncreature, non-Equipment artifact is an Equipment with equip {X} " +
                "and \"Equipped creature gets +X/+0,\" where X is that artifact's mana value.";
    }

    private BludgeonBrawlEffect(final BludgeonBrawlEffect effect) {
        super(effect);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.EQUIPMENT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    int mv = permanent.getManaValue();
                    permanent.addAbility(new SimpleStaticAbility(
                            new BoostEquippedEffect(mv, 0)
                    ), source.getSourceId(), game);
                    permanent.addAbility(new EquipAbility(mv, false), source.getSourceId(), game);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        if (layer == Layer.TypeChangingEffects_4) {
            affectedObjectList.clear();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
                affectedObjects.add(permanent);
            }
        } else {
            for (MageObjectReference mor : affectedObjectList) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    affectedObjects.add(permanent);
                }
            }
        }
        return !affectedObjects.isEmpty();
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
    public BludgeonBrawlEffect copy() {
        return new BludgeonBrawlEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
                return true;
            default:
                return false;
        }
    }
}