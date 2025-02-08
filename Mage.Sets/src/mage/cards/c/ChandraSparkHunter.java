package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.command.emblems.ChandraSparkHunterEmblem;
import mage.game.permanent.token.VehicleToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraSparkHunter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VEHICLE);

    public ChandraSparkHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(4);

        // At the beginning of combat on your turn, choose up to one target Vehicle you control. Until end of turn, it becomes an artifact creature and gains haste.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCardTypeTargetEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("choose up to one target Vehicle you control. Until end of turn, it becomes an artifact creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and gains haste"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // +2: You may sacrifice an artifact or discard a card. If you do, draw a card.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new OrCost(
                        "sacrifice an artifact or discard a card",
                        new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                        new DiscardCardCost()
                )
        ), 2));

        // +0: Create a 3/2 colorless Vehicle artifact token with crew 1.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new VehicleToken()), 0));

        // -7: You get an emblem with "Whenever an artifact you control enters, this emblem deals 3 damage to any target."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ChandraSparkHunterEmblem()), -7));
    }

    private ChandraSparkHunter(final ChandraSparkHunter card) {
        super(card);
    }

    @Override
    public ChandraSparkHunter copy() {
        return new ChandraSparkHunter(this);
    }
}
