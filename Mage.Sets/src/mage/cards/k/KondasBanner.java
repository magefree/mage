package mage.cards.k;

import java.util.UUID;
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KondasBannerColorBoostEffect()));

        // Creatures that share a creature type with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KondasBannerTypeBoostEffect()));

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
    public boolean apply(Game game, Ability source) {
        // Check if the equipment is attached
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
            if (equipedCreature != null) {
                for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    if (perm.shareCreatureTypes(game, equipedCreature)) {
                        perm.addPower(power.calculate(game, source, this));
                        perm.addToughness(toughness.calculate(game, source, this));

                    }
                }
                return true;
            }
        }
        return false;
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
    public boolean apply(Game game, Ability source) {
        // Check if the equipment is attached
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
            if (equipedCreature != null) {
                for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    if (equipedCreature.getColor(game).shares(perm.getColor(game))) {
                        perm.addPower(power.calculate(game, source, this));
                        perm.addToughness(toughness.calculate(game, source, this));

                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KondasBannerColorBoostEffect copy() {
        return new KondasBannerColorBoostEffect(this);
    }

}
