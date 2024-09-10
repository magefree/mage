package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MemoryWorm extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public MemoryWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, Memory Worm deals 2 damage to target player. That player discards a card, then draws a card. Put a +1/+1 counter on Memory Worm.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(2),
                filter, false
        );
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new DiscardTargetEffect(1)
                .setText("That player discards a card"));
        ability.addEffect(new DrawCardTargetEffect(1)
                .setText(", then draws a card"));
        ability.addEffect(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.PARADOX));
    }

    private MemoryWorm(final MemoryWorm card) {
        super(card);
    }

    @Override
    public MemoryWorm copy() {
        return new MemoryWorm(this);
    }
}
