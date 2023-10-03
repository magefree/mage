package mage.cards.q;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuestingDruid extends AdventureCard {

    private static final FilterSpell filter = new FilterSpell("a spell that's white, blue, black, or red");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)
        ));
    }

    public QuestingDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{G}", "Seek the Beast", "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's white, blue, black, or red, put a +1/+1 counter on Questing Druid.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));

        // Seek the Beast
        // Exile the top two cards of your library. Until your next end step, you may play those cards.
        this.getSpellCard().getSpellAbility().addEffect(
                new ExileTopXMayPlayUntilEndOfTurnEffect(2, false, Duration.UntilYourNextEndStep)
        );

        this.finalizeAdventure();
    }

    private QuestingDruid(final QuestingDruid card) {
        super(card);
    }

    @Override
    public QuestingDruid copy() {
        return new QuestingDruid(this);
    }
}
