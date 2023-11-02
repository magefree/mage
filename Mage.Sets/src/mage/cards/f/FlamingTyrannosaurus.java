package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FlamingTyrannosaurus extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public FlamingTyrannosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, Flaming Tyrannosaurus deals 3 damage to any target. Then put a +1/+1 counter on Flaming Tyrannosaurus.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(3),
                filter, false
        );
        ability.addEffect(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ).concatBy("Then"));
        ability.addTarget(new TargetAnyTarget());

        this.addAbility(ability.setAbilityWord(AbilityWord.PARADOX));

        // When Flaming Tyrannosaurus dies, it deals damage equal to its power to each opponent.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DamagePlayersEffect(new SourcePermanentPowerCount(), TargetController.OPPONENT)
                        .setText("it deals damage equal to its power to each opponent")
        ));
    }

    private FlamingTyrannosaurus(final FlamingTyrannosaurus card) {
        super(card);
    }

    @Override
    public FlamingTyrannosaurus copy() {
        return new FlamingTyrannosaurus(this);
    }
}
