package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GilaCourser extends CardImpl {

    public GilaCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Gila Courser attacks while saddled, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
        ));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private GilaCourser(final GilaCourser card) {
        super(card);
    }

    @Override
    public GilaCourser copy() {
        return new GilaCourser(this);
    }
}
