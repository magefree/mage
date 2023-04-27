package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author fireshoes
 */
public final class PackGuardian extends CardImpl {

    public PackGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // When Pack Guardian enters the battlefield, you may discard a land card. If you do, create a 2/2 green Wolf creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new WolfToken()),
                new DiscardCardCost(StaticFilters.FILTER_CARD_LAND_A)
        )));
    }

    private PackGuardian(final PackGuardian card) {
        super(card);
    }

    @Override
    public PackGuardian copy() {
        return new PackGuardian(this);
    }
}
