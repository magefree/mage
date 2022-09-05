package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KineticAugur extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery cards");
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public KineticAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Kinetic Augur's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue, Duration.EndOfGame)));

        // When Kinetic Augur enters the battlefield, discard up to two cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));
    }

    private KineticAugur(final KineticAugur card) {
        super(card);
    }

    @Override
    public KineticAugur copy() {
        return new KineticAugur(this);
    }
}
