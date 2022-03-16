package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpineHoundmaster extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking creatures");
    private static final FilterCard filter2
            = new FilterCard("card named Alpine Watchdog and/or a card named Igneous Cur");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(
                new NamePredicate("Alpine Watchdog"),
                new NamePredicate("Igneous Cur")
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public AlpineHoundmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Alpine Houndmaster enters the battlefield, you may search your library for a card named Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardWithDifferentNameInLibrary(0, 2, filter2), true, true
        ).setText("search your library for a card named Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle"), true));

        // Whenever Alpine Houndmaster attacks, it gets +X/+0 until end of turn, where X is the number of other attacking creatures.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"
        ), false));
    }

    private AlpineHoundmaster(final AlpineHoundmaster card) {
        super(card);
    }

    @Override
    public AlpineHoundmaster copy() {
        return new AlpineHoundmaster(this);
    }
}
