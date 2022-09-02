package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class StarfieldOfNyx extends CardImpl {

    private static final String rule1 = "As long as you control five or more enchantments, "
            + "each other non-Aura enchantment you control is a creature in addition to its other types "
            + "and has base power and base toughness each equal to its mana value.";

    private static final FilterCard filterGraveyardEnchantment
            = new FilterCard("enchantment card from your graveyard");
    private static final FilterEnchantmentPermanent filterEnchantmentYouControl
            = new FilterEnchantmentPermanent("enchantment you control");

    static {
        filterEnchantmentYouControl.add(TargetController.YOU.getControllerPredicate());
    }

    static {
        filterGraveyardEnchantment.add(CardType.ENCHANTMENT.getPredicate());
    }

    public StarfieldOfNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // At the beginning of your upkeep, you may return target enchantment card
        // from your graveyard to the battlefield.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInYourGraveyard(filterGraveyardEnchantment));
        this.addAbility(ability);

        // As long as you control five or more enchantments, each other non-Aura enchantment
        // you control is a creature in addition to its other types and has base power and
        // base toughness each equal to its converted mana cost.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new StarfieldOfNyxEffect(), new PermanentsOnTheBattlefieldCondition(
                        filterEnchantmentYouControl, ComparisonType.MORE_THAN, 4), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private StarfieldOfNyx(final StarfieldOfNyx card) {
        super(card);
    }

    @Override
    public StarfieldOfNyx copy() {
        return new StarfieldOfNyx(this);
    }

    static class StarfieldOfNyxEffect extends ContinuousEffectImpl {

        private static final FilterControlledPermanent filter
                = new FilterControlledPermanent("Each other non-Aura enchantment you control");

        static {
            filter.add(CardType.ENCHANTMENT.getPredicate());
            filter.add(Predicates.not(SubType.AURA.getPredicate()));
            filter.add(AnotherPredicate.instance);
        }

        public StarfieldOfNyxEffect() {
            super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
            staticText = "Each other non-Aura enchantment you control is a creature "
                    + "in addition to its other types and has base power and "
                    + "toughness each equal to its mana value";

            this.dependendToTypes.add(DependencyType.EnchantmentAddingRemoving); // Enchanted Evening
            this.dependendToTypes.add(DependencyType.AuraAddingRemoving); // Cloudform
            this.dependendToTypes.add(DependencyType.BecomeForest); // Song of the Dryads
            this.dependendToTypes.add(DependencyType.BecomeMountain);
            this.dependendToTypes.add(DependencyType.BecomePlains);
            this.dependendToTypes.add(DependencyType.BecomeSwamp);
            this.dependendToTypes.add(DependencyType.BecomeIsland);

            this.dependencyTypes.add(DependencyType.BecomeCreature);  // Conspiracy

        }

        public StarfieldOfNyxEffect(final StarfieldOfNyxEffect effect) {
            super(effect);
        }

        @Override
        public StarfieldOfNyxEffect copy() {
            return new StarfieldOfNyxEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter,
                    source.getControllerId(), source, game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.isCreature(game)
                                    && !permanent.hasSubtype(SubType.AURA, game)) {
                                permanent.addCardType(game, CardType.CREATURE);
                            }
                        }
                        break;

                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b
                                && permanent.isCreature(game)
                                && !permanent.hasSubtype(SubType.AURA, game)) {
                            int manaCost = permanent.getManaValue();
                            permanent.getPower().setModifiedBaseValue(manaCost);
                            permanent.getToughness().setModifiedBaseValue(manaCost);
                        }
                        break;
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
