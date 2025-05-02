package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ExhaustAbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdrenalineJockey extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell, if it's not their turn");
    private static final FilterStackObject filter2 = new FilterStackObject("an exhaust ability");

    static {
        filter.add(TargetController.INACTIVE.getControllerPredicate());
        filter2.add(ExhaustAbilityPredicate.instance);
    }

    public AdrenalineJockey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player casts a spell, if it's not their turn, this creature deals 4 damage to them.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DamageTargetEffect(4, true, "them"),
                filter, false, SetTargetPointer.PLAYER
        ));

        // Whenever you activate an exhaust ability, put a +1/+1 counter on this creature.
        this.addAbility(new ActivateAbilityTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter2, SetTargetPointer.NONE
        ));
    }

    private AdrenalineJockey(final AdrenalineJockey card) {
        super(card);
    }

    @Override
    public AdrenalineJockey copy() {
        return new AdrenalineJockey(this);
    }
}
