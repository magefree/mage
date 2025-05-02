package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnepVizierOfHazoret extends CardImpl {

    public AnepVizierOfHazoret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may exert Anep, Vizier of Hazoret as it attacks. When you do, exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn)
        )));
    }

    private AnepVizierOfHazoret(final AnepVizierOfHazoret card) {
        super(card);
    }

    @Override
    public AnepVizierOfHazoret copy() {
        return new AnepVizierOfHazoret(this);
    }
}
