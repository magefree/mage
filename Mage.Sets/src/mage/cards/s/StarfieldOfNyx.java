
package mage.cards.s;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class StarfieldOfNyx extends CardImpl {

    private static final String rule1 = "As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in addition to its other types and has base power and base toughness each equal to its converted mana cost.";

    private static final FilterCard filterGraveyardEnchantment = new FilterCard("enchantment card from your graveyard");
    private static final FilterEnchantmentPermanent filterEnchantmentYouControl = new FilterEnchantmentPermanent("enchantment you control");

    static {
        filterEnchantmentYouControl.add(new ControllerPredicate(TargetController.YOU));
    }

    static {
        filterGraveyardEnchantment.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterGraveyardEnchantment.add(new OwnerPredicate(TargetController.YOU));
    }

    public StarfieldOfNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // At the beginning of your upkeep, you may return target enchantment card from your graveyard to the battlefield.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInGraveyard(filterGraveyardEnchantment));
        this.addAbility(ability);

        // As long as you control five or more enchantments, each other non-Aura enchantment you control is a creature in addition to its other types and has base power and base toughness each equal to its converted mana cost.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new StarfieldOfNyxEffect(), new PermanentsOnTheBattlefieldCondition(filterEnchantmentYouControl, ComparisonType.MORE_THAN, 4), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public StarfieldOfNyx(final StarfieldOfNyx card) {
        super(card);
    }

    @Override
    public StarfieldOfNyx copy() {
        return new StarfieldOfNyx(this);
    }
}

class StarfieldOfNyxEffect extends ContinuousEffectImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Each other non-Aura enchantment you control");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public StarfieldOfNyxEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "Each other non-Aura enchantment you control is a creature in addition to its other types and has base power and toughness each equal to its converted mana cost";
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
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!permanent.isCreature()) {
                            permanent.addCardType(CardType.CREATURE);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int manaCost = permanent.getConvertedManaCost();
                        permanent.getPower().setValue(manaCost);
                        permanent.getToughness().setValue(manaCost);
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
        return allEffectsInLayer
                .stream()
                .filter(effect -> effect.getDependencyTypes().contains(DependencyType.AuraAddingRemoving))
                .map(Effect::getId)
                .collect(Collectors.toSet());

    }
}
