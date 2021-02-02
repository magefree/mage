package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class StormtideLeviathan extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Creatures without flying or islandwalk");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    public StormtideLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Islandwalk (This creature can't be blocked as long as defending player controls an Island.)
        this.addAbility(new IslandwalkAbility());

        // All lands are Islands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new StormtideLeviathanEffect()));

        // Creatures without flying or islandwalk can't attack.
        this.addAbility(new SimpleStaticAbility(new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private StormtideLeviathan(final StormtideLeviathan card) {
        super(card);
    }

    @Override
    public StormtideLeviathan copy() {
        return new StormtideLeviathan(this);
    }

    class StormtideLeviathanEffect extends ContinuousEffectImpl {

        private StormtideLeviathanEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
            staticText = "All lands are Islands in addition to their other types";
            this.dependencyTypes.add(DependencyType.BecomeIsland);
        }

        private StormtideLeviathanEffect(final StormtideLeviathanEffect effect) {
            super(effect);
        }

        @Override
        public StormtideLeviathanEffect copy() {
            return new StormtideLeviathanEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (Permanent land : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_LAND, source.getControllerId(), game
            )) {
                // land abilities are intrinsic, so add them here, not in layer 6
                land.addSubType(game, SubType.ISLAND);
                if (!land.getAbilities(game).containsClass(BlueManaAbility.class)) {
                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                }
            }
            return true;
        }
    }
}
