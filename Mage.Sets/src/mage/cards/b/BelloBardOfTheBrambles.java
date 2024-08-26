package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Grath
 */
public final class BelloBardOfTheBrambles extends CardImpl {

    public BelloBardOfTheBrambles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // During your turn, each non-Equipment artifact and non-Aura enchantment you control with mana value 4 or greater is a 4/4 Elemental creature in addition to its other types and has indestructible, haste, and "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BelloBardOfTheBramblesEffect(), MyTurnCondition.instance, "During your turn, each " +
                "non-Equipment artifact and non-Aura enchantment you control with mana value 4 or greater is a 4/4 " +
                "Elemental creature in addition to its other types and has indestructible, haste, and \"Whenever " +
                "this creature deals combat damage to a player, draw a card.\""
        ));
        this.addAbility(ability);
    }

    private BelloBardOfTheBrambles(final BelloBardOfTheBrambles card) {
        super(card);
    }

    @Override
    public BelloBardOfTheBrambles copy() {
        return new BelloBardOfTheBrambles(this);
    }
}

class BelloBardOfTheBramblesEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.and(
                Predicates.or(
                        Predicates.and(CardType.ARTIFACT.getPredicate(), Predicates.not(SubType.EQUIPMENT.getPredicate())),
                        Predicates.and(CardType.ENCHANTMENT.getPredicate(), Predicates.not(SubType.AURA.getPredicate()))
                ),
                new ManaValuePredicate(ComparisonType.OR_GREATER, 4)
        ));
    }

    public BelloBardOfTheBramblesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "each non-Equipment artifact and non-Aura enchantment you control with mana value 4 or greater " +
                "is a 4/4 Elemental creature in addition to its other types and has indestructible, haste, and " +
                "\"Whenever this creature deals combat damage to a player, draw a card.\"";

        this.dependendToTypes.add(DependencyType.EnchantmentAddingRemoving); // Enchanted Evening
        this.dependendToTypes.add(DependencyType.AuraAddingRemoving); // Cloudform
        this.dependendToTypes.add(DependencyType.BecomeForest); // Song of the Dryads
        this.dependendToTypes.add(DependencyType.BecomeMountain);
        this.dependendToTypes.add(DependencyType.BecomePlains);
        this.dependendToTypes.add(DependencyType.BecomeSwamp);
        this.dependendToTypes.add(DependencyType.BecomeIsland);

        this.dependencyTypes.add(DependencyType.BecomeCreature);  // Conspiracy
    }

    private BelloBardOfTheBramblesEffect(final BelloBardOfTheBramblesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addCardType(game, CardType.CREATURE);
                    permanent.addSubType(game, SubType.ELEMENTAL);
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                        permanent.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false, false), source.getSourceId(), game);
                    }
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(4);
                        permanent.getToughness().setModifiedBaseValue(4);
                    }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.PTChangingEffects_7;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BelloBardOfTheBramblesEffect copy() {
        return new BelloBardOfTheBramblesEffect(this);
    }
}