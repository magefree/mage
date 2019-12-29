package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SetessanChampion extends CardImpl {

    public SetessanChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Constellation — Whenever an enchantment enters the battlefield under your control, put a +1/+1 counter on Setessan Champion and draw a card.
        Ability ability = new ConstellationAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SetessanChampion(final SetessanChampion card) {
        super(card);
    }

    @Override
    public SetessanChampion copy() {
        return new SetessanChampion(this);
    }
}
