package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;

/**
 * @author muz
 */
public final class LokiLaufeyson extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell(
        "an instant or sorcery spell with mana value less than or equal to Loki's power"
    );

    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
    }

    public LokiLaufeyson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}, {T}: When you next cast an instant or sorcery spell with mana value less than or equal to Loki's power this turn, copy that spell. You may choose new targets for the copy.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new CopyNextSpellDelayedTriggeredAbility(filter)
        ).setText("When you next cast an instant or sorcery spell with mana value less than or equal " +
                "to Loki's power this turn, copy that spell. You may choose new targets for the copy");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Power-up -- {4}{R}: Put two +1/+1 counters on Loki.
        this.addAbility(new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{4}{R}")
        ));
    }

    private LokiLaufeyson(final LokiLaufeyson card) {
        super(card);
    }

    @Override
    public LokiLaufeyson copy() {
        return new LokiLaufeyson(this);
    }
}
