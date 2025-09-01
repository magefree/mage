package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;
import java.util.UUID;

/**
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
        this.addAbility(new SimpleStaticAbility(new BelloBardOfTheBramblesEffect()));
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
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(Predicates.not(SubType.EQUIPMENT.getPredicate()));
        filter.add(Predicates.not(SubType.AURA.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    public BelloBardOfTheBramblesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "during your turn, each non-Equipment artifact and non-Aura enchantment you control " +
                "with mana value 4 or greater is a 4/4 Elemental creature in addition to its other types and has " +
                "indestructible, haste, and \"Whenever this creature deals combat damage to a player, draw a card.\"";

        this.dependendToTypes.add(DependencyType.EnchantmentAddingRemoving); // Enchanted Evening
        this.dependendToTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines

        this.dependencyTypes.add(DependencyType.BecomeCreature);  // Conspiracy
    }

    private BelloBardOfTheBramblesEffect(final BelloBardOfTheBramblesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (!source.isControlledBy(game.getActivePlayerId())) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                affectedObjectList.clear();
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    permanent.addCardType(game, CardType.CREATURE);
                    permanent.addSubType(game, SubType.ELEMENTAL);
                    affectedObjectList.add(new MageObjectReference(permanent, game));
                }
                return true;
            case AbilityAddingRemovingEffects_6:
                for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                    Permanent permanent = it.next().getPermanent(game);
                    if (permanent == null) {
                        continue;
                    }
                    permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                    permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                    permanent.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)), source.getSourceId(), game);
                }
                return true;
            case PTChangingEffects_7:
                if (sublayer != SubLayer.SetPT_7b) {
                    return false;
                }
                for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
                    Permanent permanent = it.next().getPermanent(game);
                    if (permanent == null) {
                        continue;
                    }
                    permanent.getPower().setModifiedBaseValue(4);
                    permanent.getToughness().setModifiedBaseValue(4);
                }
                return true;
            default:
                return false;
        }
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
