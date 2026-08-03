package mage.cards.d;

import java.util.UUID;
import mage.filter.predicate.Predicates;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 * @author muz
 */
public final class DainsCompany extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DWARF);
    private static final FilterCard filter2 = new FilterCard("a Dwarf or Equipment card");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(
            SubType.DWARF.getPredicate(),
            SubType.EQUIPMENT.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public DainsCompany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature has lifelink as long as you control another Dwarf.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(LifelinkAbility.getInstance()), condition,
            "{this} has lifelink as long as you control another Dwarf"
        )));

        // When this creature enters, look at the top four cards of your library. You may reveal a Dwarf or Equipment card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
            4, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private DainsCompany(final DainsCompany card) {
        super(card);
    }

    @Override
    public DainsCompany copy() {
        return new DainsCompany(this);
    }
}
