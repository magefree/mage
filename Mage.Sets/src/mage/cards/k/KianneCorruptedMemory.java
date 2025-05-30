package mage.cards.k;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 *
 * @author Grath
 */
public final class KianneCorruptedMemory extends CardImpl {
    private static final FilterCard filter = new FilterCard("noncreature spells");
    private static final FilterCard filter2 = new FilterCard("creature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(CardType.CREATURE.getPredicate());
    }


    public KianneCorruptedMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as Kianne's power is even, you may cast noncreature spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalAsThoughEffect(
                        new CastAsThoughItHadFlashAllEffect(Duration.Custom, filter),
                        KianneCorruptedMemoryEvenCondition.instance
        ).setText("As long as {this}'s power is even, you may cast noncreature spells as though they had flash.")));

        // As long as Kianne's power is odd, you may cast creature spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalAsThoughEffect(
                        new CastAsThoughItHadFlashAllEffect(Duration.Custom, filter2),
                        KianneCorruptedMemoryOddCondition.instance
        ).setText("As long as {this}'s power is odd, you may cast creature spells as though they had flash.")));

        // Whenever you draw a card, put a +1/+1 counter on Kianne.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private KianneCorruptedMemory(final KianneCorruptedMemory card) {
        super(card);
    }

    @Override
    public KianneCorruptedMemory copy() {
        return new KianneCorruptedMemory(this);
    }
}

enum KianneCorruptedMemoryOddCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0) % 2 == 1;
    }
}

enum KianneCorruptedMemoryEvenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0) % 2 == 0;
    }
}