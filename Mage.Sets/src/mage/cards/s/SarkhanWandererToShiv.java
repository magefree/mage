package mage.cards.s;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.DynamicCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author karapuzz14
 */
public final class SarkhanWandererToShiv extends CardImpl {

    public SarkhanWandererToShiv(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);
        this.setStartingLoyalty(4);

        // +1: Dragon cards in your hand perpetually gain "This spell costs {1} less to cast," and "You may pay {X} rather than pay this spell's mana cost, where X is its mana value."
        LoyaltyAbility perpetuallyAbility = new LoyaltyAbility(new SarkhanWandererToShivEffect(), 1);
        this.addAbility(perpetuallyAbility);

        // +1: Conjure a Shivan Dragon card into your hand.
        LoyaltyAbility conjureAbility = new LoyaltyAbility(new ConjureCardEffect("Shivan Dragon"), 1);
        this.addAbility(conjureAbility);

        // −2: Sarkhan, Wanderer to Shiv deals 3 damage to target creature.
        LoyaltyAbility damageAbility = new LoyaltyAbility(new DamageTargetEffect(3), -2);
        damageAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(damageAbility);
    }

    private SarkhanWandererToShiv(final SarkhanWandererToShiv card) {
        super(card);
    }

    @Override
    public SarkhanWandererToShiv copy() {
        return new SarkhanWandererToShiv(this);
    }
}

class SarkhanWandererToShivEffect extends OneShotEffect {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(
            SourceIsSpellCondition.instance, "You may pay {X} rather than pay this spell's mana cost, where X is its mana value.",
            new FilterCard(), true, new ColorlessManaValue());
    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }
    SarkhanWandererToShivEffect() {
        super(Outcome.Benefit);
        this.staticText = "Dragon cards in your hand perpetually gain \"This spell costs {1} less to cast\", and \"You may pay {X} rather than pay this spell's mana cost, where X is its mana value.\"";
    }

    private SarkhanWandererToShivEffect(final SarkhanWandererToShivEffect effect) {
        super(effect);
    }

    @Override
    public SarkhanWandererToShivEffect copy() {
        return new SarkhanWandererToShivEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null && !controller.getHand().isEmpty()) {
            Set<Card> dragonCards = controller.getHand().getCards(filter, game);
            if (dragonCards == null) {
                return false;
            }
            TargetPointer pointer = new FixedTargets(dragonCards, game);

            ContinuousEffect altCostEffect = new GainAbilityTargetPerpetuallyEffect(alternativeCastingCostAbility.setRuleAtTheTop(false)).setTargetPointer(pointer);
            game.addEffect(altCostEffect, source);

            ContinuousEffect reduceCostEffect = new GainAbilityTargetPerpetuallyEffect(new SimpleStaticAbility(
                    Zone.ALL, new SpellCostReductionSourceEffect(1)
            )).setTargetPointer(pointer);
            game.addEffect(reduceCostEffect, source);

        }
        return true;
    }
}

class ColorlessManaValue implements DynamicCost {

    @Override
    public Cost getCost(Ability ability, Game game) {
        return new GenericManaCost(ability.getManaCosts().manaValue());
    }

    @Override
    public String getText(Ability ability, Game game) {
        return "Pay " + getCost(ability, game).getText() + " rather than " + ability.getManaCosts().getText() + " for " + ability.getSourceObject(game).getIdName() + "?";
    }
}