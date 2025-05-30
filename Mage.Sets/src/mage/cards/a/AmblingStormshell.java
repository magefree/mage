package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmblingStormshell extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Turtle spell");

    static {
        filter.add(SubType.TURTLE.getPredicate());
    }

    public AmblingStormshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(9);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever this creature attacks, put three stun counters on it and draw three cards.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersSourceEffect(CounterType.STUN.createInstance(3))
                        .setText("put three stun counters on it")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy("and"));
        this.addAbility(ability);

        // Whenever you cast a Turtle spell, untap this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));
    }

    private AmblingStormshell(final AmblingStormshell card) {
        super(card);
    }

    @Override
    public AmblingStormshell copy() {
        return new AmblingStormshell(this);
    }
}
