package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ProftConsultingDetective extends CardImpl {

    public ProftConsultingDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you scry or surveil, you may pay {2}. If you do, put a +1/+1 counter on Proft and draw a card.
        this.addAbility(new ScryOrSurveilTriggeredAbility(
            new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{2}")
            ).addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"))
        ));
    }

    private ProftConsultingDetective(final ProftConsultingDetective card) {
        super(card);
    }

    @Override
    public ProftConsultingDetective copy() {
        return new ProftConsultingDetective(this);
    }
}
