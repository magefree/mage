package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class SpellgorgerWeirdToken extends TokenImpl {

    public SpellgorgerWeirdToken() {
        super("Spellgorger Weird", "Spellgorger Weird token");
        manaCost = new ManaCostsImpl<>("{2}{R}");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.WEIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Spellgorger Weird.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SpellgorgerWeirdToken(final SpellgorgerWeirdToken token) {
        super(token);
    }

    @Override
    public SpellgorgerWeirdToken copy() {
        return new SpellgorgerWeirdToken(this);
    }
}
