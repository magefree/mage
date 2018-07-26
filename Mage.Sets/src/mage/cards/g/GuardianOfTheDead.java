
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.effects.common.ExileCardFromOwnGraveyardControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author EikePeace
 */
public final class GuardianOfTheDead extends CardImpl {

    public GuardianOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cards in graveyards can't be the targets of spells or abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedCardsGraveyardsEffect()));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GuardianOfTheDeadExileFilterEffect()));
    }

    public GuardianOfTheDead(final GuardianOfTheDead card) {
        super(card);
    }

    @Override
    public GuardianOfTheDead copy() {
        return new GuardianOfTheDead(this);
    }
}


class GuardianOfTheDeadExileFilterEffect extends CostModificationEffectImpl {

    public GuardianOfTheDeadExileFilterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.SET_COST);
        staticText = "Players can't exile cards from graveyardsto cast spells or activate abilities";
    }

    protected GuardianOfTheDeadExileFilterEffect(GuardianOfTheDeadExileFilterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        for (Cost cost : abilityToModify.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                ExileFromGraveCost ExileCost = (ExileFromGraveCost) cost;
                Filter filter = ExileCost.getTargets().get(0).getFilter();
                filter.add(Predicates.not((
                        Predicates.or(
                                new CardTypePredicate(CardType.CREATURE),
                                new CardTypePredicate(CardType.ENCHANTMENT),
                                new CardTypePredicate(CardType.ARTIFACT),
                                new CardTypePredicate(CardType.INSTANT),
                                new CardTypePredicate(CardType.SORCERY),
                                new CardTypePredicate(CardType.PLANESWALKER),
                                new CardTypePredicate(CardType.LAND)))));
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || abilityToModify instanceof SpellAbility;
    }

    @Override
    public GuardianOfTheDeadExileFilterEffect copy() {
        return new GuardianOfTheDeadExileFilterEffect(this);
    }

}
