package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BullseyeDeathDealer extends CardImpl {

    public BullseyeDeathDealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Bullseye enters, you may sacrifice an artifact or discard a nonland card. When you do, Bullseye deals 2 damage to any target.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new DamageTargetEffect(2), false);
        reflexive.addTarget(new TargetAnyTarget());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            reflexive,
            new OrCost(
                "Sacrifice an artifact or discard a nonland card?",
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN),
                new DiscardCardCost(StaticFilters.FILTER_CARD_NON_LAND)
            ),
            "Sacrifice an artifact or discard a nonland card?"
        )));

        // {3}, {T}, Sacrifice an artifact or discard a nonland card: Bullseye deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new OrCost(
            "Sacrifice an artifact or discard a nonland card?",
            new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN),
            new DiscardCardCost(StaticFilters.FILTER_CARD_NON_LAND)
        ));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BullseyeDeathDealer(final BullseyeDeathDealer card) {
        super(card);
    }

    @Override
    public BullseyeDeathDealer copy() {
        return new BullseyeDeathDealer(this);
    }
}
