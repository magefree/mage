package mage.cards.u;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class UrborgTombOfYawgmoth extends CardImpl {

    public UrborgTombOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // Each land is a Swamp in addition to its other land types.
        Ability ability = new SimpleStaticAbility(new UrborgTombOfYawgmothEffect());
        this.addAbility(ability);

    }

    public UrborgTombOfYawgmoth(final UrborgTombOfYawgmoth card) {
        super(card);
    }

    @Override
    public UrborgTombOfYawgmoth copy() {
        return new UrborgTombOfYawgmoth(this);
    }

    class UrborgTombOfYawgmothEffect extends ContinuousEffectImpl {

        UrborgTombOfYawgmothEffect() {
            super(Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
            this.staticText = "Each land is a Swamp in addition to its other land types";
            this.dependencyTypes.add(DependencyType.BecomeSwamp);
        }

        UrborgTombOfYawgmothEffect(final UrborgTombOfYawgmothEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public UrborgTombOfYawgmothEffect copy() {
            return new UrborgTombOfYawgmothEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                        // So the ability removing has to be done before Layer 6
                        // Lands have their mana ability intrinsically, so that is added in layer 4
                        if (!land.getSubtype(game).contains(SubType.SWAMP)) {
                            land.getSubtype(game).add(SubType.SWAMP);
                        }
                        if (!land.getAbilities().containsRule(new BlackManaAbility())) {
                            land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                        }
                        break;
                }
            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
        }

        @Override
        public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
            // the dependent classes needs to be an enclosed class for dependent check of continuous effects
            return allEffectsInLayer.stream()
                    .filter(effect -> mage.cards.b.BloodMoon.class.equals(effect.getClass().getEnclosingClass()))
                    .map(Effect::getId)
                    .collect(Collectors.toSet()); // Blood Moon affects non-basic land like Urborg

        }
    }
}
