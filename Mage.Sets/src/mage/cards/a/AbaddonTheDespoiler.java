package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.GainAbilitySpellsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.DynamicManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbaddonTheDespoiler extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total life lost by opponents this turn", OpponentsLostLifeCount.instance
    );

    public AbaddonTheDespoiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Mark of the Chaos Ascendant — During your turn, spells you cast from your hand with mana value X or less have cascade, where X is the total amount of life your opponents have lost this turn.
        final FilterSpell filter = new FilterSpell();
        Ability markOfTheChaosAscendant = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySpellsEffect(new CascadeAbility(false), filter),
                MyTurnCondition.instance, "during your turn, spells you cast from " +
                "your hand with mana value X or less have cascade, where X is the " +
                "total amount of life your opponents have lost this turn"
        ));
        filter.add(new CastFromZonePredicate(Zone.HAND));
        filter.add(new DynamicManaValuePredicate(ComparisonType.FEWER_THAN,
                new IntPlusDynamicValue(1, OpponentsLostLifeCount.instance), // Add one to convert <= to <
                markOfTheChaosAscendant, null));
        this.addAbility(markOfTheChaosAscendant.addHint(hint));
    }

    private AbaddonTheDespoiler(final AbaddonTheDespoiler card) {
        super(card);
    }

    @Override
    public AbaddonTheDespoiler copy() {
        return new AbaddonTheDespoiler(this);
    }
}
