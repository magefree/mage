package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BristlebudFarmer extends CardImpl {

    public BristlebudFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Bristlebud Farmer enters the battlefield, create two Food tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 2)));

        // Whenever Bristlebud Farmer attacks, you may sacrifice a Food. If you do, mill three cards. You may put a permanent card from among them into your hand.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_PERMANENT),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD)
        )));
    }

    private BristlebudFarmer(final BristlebudFarmer card) {
        super(card);
    }

    @Override
    public BristlebudFarmer copy() {
        return new BristlebudFarmer(this);
    }
}
