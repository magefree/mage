package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornplateIntimidator extends CardImpl {

    public ThornplateIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Offspring {3}
        this.addAbility(new OffspringAbility("{3}"));

        // When this creature enters, target opponent loses 3 life unless they sacrifice a nonland permanent or discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                new LoseLifeTargetEffect(3),
                new OrCost(
                        "sacrifice a nonland permanent or discard a card",
                        new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND), new DiscardCardCost()
                ),
                "Sacrifice a nonland permanent or discard a card to prevent losing 3 life?"
        ).setText("target opponent loses 3 life unless they sacrifice a nonland permanent of their choice or discard a card"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThornplateIntimidator(final ThornplateIntimidator card) {
        super(card);
    }

    @Override
    public ThornplateIntimidator copy() {
        return new ThornplateIntimidator(this);
    }
}
