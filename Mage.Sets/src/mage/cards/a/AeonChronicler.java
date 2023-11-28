
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.CounterRemovedFromSourceWhileExiledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AeonChronicler extends CardImpl {

    public AeonChronicler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Aeon Chronicler's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CardsInControllerHandCount.instance)));

        // Suspend X-{X}{3}{U}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{3}{U}"), this, true));

        // Whenever a time counter is removed from Aeon Chronicler while it's exiled, draw a card.
        this.addAbility(new CounterRemovedFromSourceWhileExiledTriggeredAbility(CounterType.TIME, new DrawCardSourceControllerEffect(1)));
    }

    private AeonChronicler(final AeonChronicler card) {
        super(card);
    }

    @Override
    public AeonChronicler copy() {
        return new AeonChronicler(this);
    }
}