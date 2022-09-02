
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public final class Splinterfright extends CardImpl {

    public Splinterfright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(TrampleAbility.getInstance());
        // Splinterfright's power and toughness are each equal to the number of creature cards in your graveyard.
        CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(count, Duration.EndOfGame)));
        // At the beginning of your upkeep, put the top two cards of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new MillCardsControllerEffect(2), false));
    }

    private Splinterfright(final Splinterfright card) {
        super(card);
    }

    @Override
    public Splinterfright copy() {
        return new Splinterfright(this);
    }
}
