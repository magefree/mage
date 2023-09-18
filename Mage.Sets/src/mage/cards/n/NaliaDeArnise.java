package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaliaDeArnise extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast Cleric, Rogue, Warrior, and Wizard spells");

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    public NaliaDeArnise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast Cleric, Rogue, Warrior, and Wizard spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        )));

        // At the beginning of combat on your turn, if you have a full party, put a +1/+1 counter on each creature you control and those creatures gain deathtouch until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                ), TargetController.YOU, false), FullPartyCondition.instance, "At the beginning " +
                "of combat on your turn, if you have a full party, put a +1/+1 counter on each creature " +
                "you control and those creatures gain deathtouch until end of turn."
        );
        ability.addEffect(new GainAbilityAllEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private NaliaDeArnise(final NaliaDeArnise card) {
        super(card);
    }

    @Override
    public NaliaDeArnise copy() {
        return new NaliaDeArnise(this);
    }
}
