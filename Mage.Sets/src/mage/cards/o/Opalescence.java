package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Mitchel Stein
 */
public final class Opalescence extends CardImpl {

    public Opalescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Each other non-Aura enchantment is a creature with power and 
        // toughness each equal to its converted mana cost. It's still an enchantment.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpalescenceEffect()));

    }

    private Opalescence(final Opalescence card) {
        super(card);
    }

    @Override
    public Opalescence copy() {
        return new Opalescence(this);
    }

    // needs to enclosed with the card class due to a dependency check by cards like Conspiracy
    static class OpalescenceEffect extends ContinuousEffectImpl {

        private static final FilterEnchantmentPermanent filter
                = new FilterEnchantmentPermanent("Each other non-Aura enchantment");

        static {
            filter.add(Predicates.not(SubType.AURA.getPredicate()));
            filter.add(AnotherPredicate.instance);
        }

        public OpalescenceEffect() {
            super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
            staticText = "Each other non-Aura enchantment is a creature in addition to its other "
                    + "types and has base power and base toughness each equal to its mana value";
            
            this.dependendToTypes.add(DependencyType.EnchantmentAddingRemoving); // Enchanted Evening
            this.dependendToTypes.add(DependencyType.AuraAddingRemoving); // Cloudform
            
            this.dependencyTypes.add(DependencyType.BecomeCreature);  // Conspiracy
        }

        public OpalescenceEffect(final OpalescenceEffect effect) {
            super(effect);
        }

        @Override
        public OpalescenceEffect copy() {
            return new OpalescenceEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, 
                    source.getControllerId(), source, game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.isCreature(game)) {
                                permanent.addCardType(game, CardType.CREATURE);
                            }
                        }
                        break;

                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            int manaCost = permanent.getManaValue();
                            permanent.getPower().setModifiedBaseValue(manaCost);
                            permanent.getToughness().setModifiedBaseValue(manaCost);
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
            return layer == Layer.PTChangingEffects_7
                    || layer == Layer.TypeChangingEffects_4;
        }
    }
}
