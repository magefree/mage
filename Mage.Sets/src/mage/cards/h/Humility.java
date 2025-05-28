package mage.cards.h;

import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Humility extends CardImpl {

    public Humility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // All creatures lose all abilities and have base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(
                new HumilityEffect(Duration.WhileOnBattlefield)));

    }

    private Humility(final Humility card) {
        super(card);
    }

    @Override
    public Humility copy() {
        return new Humility(this);
    }

    static class HumilityEffect extends ContinuousEffectImpl {

        public HumilityEffect(Duration duration) {
            super(duration, Outcome.LoseAbility);
            staticText = "All creatures lose all abilities and have base power and toughness 1/1";
            this.effectCardZones.add(Zone.BATTLEFIELD);
            this.affectedPermanentFilter = StaticFilters.FILTER_PERMANENT_CREATURE;
        }

        private HumilityEffect(final HumilityEffect effect) {
            super(effect);
        }

        @Override
        public HumilityEffect copy() {
            return new HumilityEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (MageObjectReference mageObjectReference : affectedObjectList) {
                Permanent permanent = mageObjectReference.getPermanent(game);
                if (permanent == null) {
                    continue;
                }
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setModifiedBaseValue(1);
                            permanent.getToughness().setModifiedBaseValue(1);
                        }
                }
            }
            return true;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6
                    || layer == Layer.PTChangingEffects_7;
        }

        @Override
        public boolean hasSubLayer(SubLayer sublayer) {
            return sublayer == SubLayer.NA
                    || sublayer == SubLayer.SetPT_7b;
        }
    }
}
