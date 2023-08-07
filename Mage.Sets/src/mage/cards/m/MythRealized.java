package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author LevelX2
 */
public final class MythRealized extends CardImpl {

    private static final DynamicValue loreCounterCount = new CountersSourceCount(CounterType.LORE);
    
    public MythRealized(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // Whenever you cast a noncreature spell, put a lore counter on Myth Realized.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));

        // {2}{W}: Put a lore counter on Myth Realized.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ManaCostsImpl<>("{2}{W}")));

        // {W}: Until end of turn, Myth Realized becomes a Monk Avatar creature in addition to its other types and gains "This creature's power and toughness are each equal to the number of lore counters on it."
        Effect effect = new BecomesCreatureSourceEffect(new MythRealizedToken(), CardType.ENCHANTMENT, Duration.EndOfTurn).withDurationRuleAtStart(true);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{W}"));
        ability.addEffect(new SetBasePowerToughnessSourceEffect(loreCounterCount, loreCounterCount, Duration.EndOfTurn, SubLayer.SetPT_7b).setText("and gains \"This creature's power and toughness are each equal to the number of lore counters on it.\""));

        this.addAbility(ability);
    }

    private MythRealized(final MythRealized card) {
        super(card);
    }

    @Override
    public MythRealized copy() {
        return new MythRealized(this);
    }
}

class MythRealizedToken extends TokenImpl {

    public MythRealizedToken() {
        super("", "Monk Avatar creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MONK);
        subtype.add(SubType.AVATAR);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }
    public MythRealizedToken(final MythRealizedToken token) {
        super(token);
    }

    @Override
    public MythRealizedToken copy() {
        return new MythRealizedToken(this);
    }
}
