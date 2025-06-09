package mage.cards.k;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.AttachableToRestrictedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class KondasBanner extends CardImpl {

    private static final FilterControlledCreaturePermanent legendaryFilter = new FilterControlledCreaturePermanent("legendary creature");

    static {
        legendaryFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public KondasBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        Target target = new TargetControlledCreaturePermanent(1, 1, legendaryFilter, false);
        // Konda's Banner can be attached only to a legendary creature.
        this.addAbility(new AttachableToRestrictedAbility(target));

        // Creatures that share a color with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(new KondasBannerColorBoostEffect()));

        // Creatures that share a creature type with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(new KondasBannerTypeBoostEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), target, false));

    }

    private KondasBanner(final KondasBanner card) {
        super(card);
    }

    @Override
    public KondasBanner copy() {
        return new KondasBanner(this);
    }
}

class KondasBannerTypeBoostEffect extends BoostAllEffect {

    private static final String effectText = "Creatures that share a creature type with equipped creature get +1/+1";

    KondasBannerTypeBoostEffect() {
        super(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        staticText = effectText;
    }

    private KondasBannerTypeBoostEffect(final KondasBannerTypeBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
        if (equippedCreature != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.shareCreatureTypes(game, equippedCreature)) {
                    affectedObjects.add(permanent);
                }
            }
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public KondasBannerTypeBoostEffect copy() {
        return new KondasBannerTypeBoostEffect(this);
    }

}

class KondasBannerColorBoostEffect extends BoostAllEffect {

    private static final String effectText = "Creatures that share a color with equipped creature get +1/+1.";

    KondasBannerColorBoostEffect() {
        super(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        staticText = effectText;
    }

    private KondasBannerColorBoostEffect(final KondasBannerColorBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
        if (equippedCreature != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.getColor(game).shares(equippedCreature.getColor(game))) {
                    affectedObjects.add(permanent);
                }
            }
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public KondasBannerColorBoostEffect copy() {
        return new KondasBannerColorBoostEffect(this);
    }

}
