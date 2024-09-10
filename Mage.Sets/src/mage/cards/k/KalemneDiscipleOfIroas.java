package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KalemneDiscipleOfIroas extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with mana value 5 or greater");

    static {
        filterSpell.add(CardType.CREATURE.getPredicate());
        filterSpell.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public KalemneDiscipleOfIroas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a creature spell with converted mana cost 5 or greater, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), filterSpell, false));

        // Kalemne, Disciple of Iroas gets +1/+1 for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(
                        SourceControllerCountersCount.EXPERIENCE,
                        SourceControllerCountersCount.EXPERIENCE,
                        Duration.WhileOnBattlefield
                )
        ));
    }

    private KalemneDiscipleOfIroas(final KalemneDiscipleOfIroas card) {
        super(card);
    }

    @Override
    public KalemneDiscipleOfIroas copy() {
        return new KalemneDiscipleOfIroas(this);
    }
}