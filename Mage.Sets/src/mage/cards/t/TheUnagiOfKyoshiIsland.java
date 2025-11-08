package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheUnagiOfKyoshiIsland extends CardImpl {

    public TheUnagiOfKyoshiIsland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Ward--Waterbend {4}
        this.addAbility(new WardAbility(new WaterbendCost(4)));

        // Whenever an opponent draws their second card each turn, draw two cards.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new DrawCardSourceControllerEffect(2),
                false, TargetController.OPPONENT, 2
        ));
    }

    private TheUnagiOfKyoshiIsland(final TheUnagiOfKyoshiIsland card) {
        super(card);
    }

    @Override
    public TheUnagiOfKyoshiIsland copy() {
        return new TheUnagiOfKyoshiIsland(this);
    }
}
