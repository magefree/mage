package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ColleenWingStreetSamurai extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public ColleenWingStreetSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a spell that targets a creature you control, put a +1/+1 counter on Colleen Wing. Scry 1.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        );
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);
    }

    private ColleenWingStreetSamurai(final ColleenWingStreetSamurai card) {
        super(card);
    }

    @Override
    public ColleenWingStreetSamurai copy() {
        return new ColleenWingStreetSamurai(this);
    }
}
