package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SeaGateOracle extends CardImpl {

    public SeaGateOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Sea Gate Oracle enters the battlefield, look at the top two cards of your library.
        // Put one of them into your hand and the other on the bottom of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.BOTTOM_ANY)));
    }


    private SeaGateOracle(final SeaGateOracle card) {
        super(card);
    }

    @Override
    public SeaGateOracle copy() {
        return new SeaGateOracle(this);
    }
}
