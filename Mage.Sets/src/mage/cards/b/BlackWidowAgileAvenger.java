package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlackWidowAgileAvenger extends CardImpl {

    public BlackWidowAgileAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever an opponent draws their second card each turn, put a +1/+1 counter on Black Widow and you draw a card.
        Ability ability = new DrawNthCardTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, TargetController.OPPONENT, 2
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1, true).concatBy("and"));
        this.addAbility(ability);
    }

    private BlackWidowAgileAvenger(final BlackWidowAgileAvenger card) {
        super(card);
    }

    @Override
    public BlackWidowAgileAvenger copy() {
        return new BlackWidowAgileAvenger(this);
    }
}
