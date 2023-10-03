package mage.cards.m;

import mage.MageInt;
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
public final class MagusOfTheMoon extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public MagusOfTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MagusOfTheMoonEffect()));
    }

    private MagusOfTheMoon(final MagusOfTheMoon card) {
        super(card);
    }

    @Override
    public MagusOfTheMoon copy() {
        return new MagusOfTheMoon(this);
    }

    static class MagusOfTheMoonEffect extends ContinuousEffectImpl {

        MagusOfTheMoonEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Detriment);
            this.staticText = "Nonbasic lands are Mountains";
            dependencyTypes.add(DependencyType.BecomeMountain);
        }

        private MagusOfTheMoonEffect(final MagusOfTheMoonEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public MagusOfTheMoonEffect copy() {
            return new MagusOfTheMoonEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                        // So the ability removing has to be done before Layer 6
                        land.removeAllAbilities(source.getSourceId(), game);
                        land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                        land.addSubType(game, SubType.MOUNTAIN);
                        // Mountains have the red mana ability intrinsically so the ability must be added in this layer
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
