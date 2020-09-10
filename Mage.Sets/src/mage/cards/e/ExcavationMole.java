package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExcavationMole extends CardImpl {

    public ExcavationMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MOLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Excavation Mole enters the battlefield, put the top three cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsControllerEffect(3)
        ));
    }

    private ExcavationMole(final ExcavationMole card) {
        super(card);
    }

    @Override
    public ExcavationMole copy() {
        return new ExcavationMole(this);
    }
}
