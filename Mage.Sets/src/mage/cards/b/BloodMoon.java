package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BloodMoon extends CardImpl {

    public BloodMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodMoonEffect()));
    }

    private BloodMoon(final BloodMoon card) {
        super(card);
    }

    @Override
    public BloodMoon copy() {
        return new BloodMoon(this);
    }

    static class BloodMoonEffect extends ContinuousEffectImpl {

        private static final FilterLandPermanent filter = new FilterLandPermanent();

        static {
            filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        }

        BloodMoonEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Detriment);
            this.staticText = "Nonbasic lands are Mountains";
            this.dependencyTypes.add(DependencyType.BecomeMountain);
            this.dependendToTypes.add(DependencyType.BecomeNonbasicLand);
        }

        BloodMoonEffect(final BloodMoonEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public BloodMoonEffect copy() {
            return new BloodMoonEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                        // So the ability removing has to be done before Layer 6
                        // Lands have their mana ability intrinsically, so that is added in layer 4
                        land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                        land.addSubType(game, SubType.MOUNTAIN);
                        land.removeAllAbilities(source.getSourceId(), game);
                        land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        break;
                }
            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
        }
    }
}
