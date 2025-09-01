package mage.cards.g;

import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.WurmToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GarrukPrimalHunter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public GarrukPrimalHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.setStartingLoyalty(3);

        // +1: Create a 3/3 green Beast creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BeastToken()), 1));

        // -3: Draw cards equal to the greatest power among creatures you control.
        this.addAbility(new LoyaltyAbility(
                new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
                        .setText("Draw cards equal to the greatest power among creatures you control"),
                -3).addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // -6: Create a 6/6 green Wurm creature token for each land you control.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WurmToken(), new PermanentsOnBattlefieldCount(filter)), -6));
    }

    private GarrukPrimalHunter(final GarrukPrimalHunter card) {
        super(card);
    }

    @Override
    public GarrukPrimalHunter copy() {
        return new GarrukPrimalHunter(this);
    }

}