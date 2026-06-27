package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ContractHero extends CardImpl {

    public ContractHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Whenever this creature attacks, you may sacrifice an artifact or discard a card. If you do, this creature gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
            new BoostSourceEffect(2, 0, Duration.EndOfTurn),
            new OrCost(
                "sacrifice an artifact or discard a card",
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                new DiscardCardCost()
            )
        )));
    }

    private ContractHero(final ContractHero card) {
        super(card);
    }

    @Override
    public ContractHero copy() {
        return new ContractHero(this);
    }
}
