package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BearToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlaxenIntruder extends AdventureCard {

    public FlaxenIntruder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{G}", "Welcome Home", "{5}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Flaxen Intruder deals combat damage to a player, you may sacrifice it. When you do, destroy target artifact or enchantment.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DestroyTargetEffect(), false, "destroy target artifact or enchantment"
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        Cost cost = new SacrificeSourceCost();
        cost.setText("sacrifice it");
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoWhenCostPaid(
                ability, cost, "Sacrifice {this}?"
        ), false));

        // Welcome Home
        // Create three 2/2 green Bear creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new BearToken(), 3));

        this.finalizeAdventure();
    }

    private FlaxenIntruder(final FlaxenIntruder card) {
        super(card);
    }

    @Override
    public FlaxenIntruder copy() {
        return new FlaxenIntruder(this);
    }
}
