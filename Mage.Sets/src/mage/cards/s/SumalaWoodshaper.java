package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SumalaWoodshaper extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a creature or enchantment card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)
        ));
    }

    public SumalaWoodshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Sumala Woodshaper enters the battlefield, look at the top four cards of your library. You may reveal a creature or enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                new StaticValue(4), false, new StaticValue(1), filter, Zone.LIBRARY, false,
                true, false, Zone.HAND, false, false, false
        ).setBackInRandomOrder(true), false));
    }

    public SumalaWoodshaper(final SumalaWoodshaper card) {
        super(card);
    }

    @Override
    public SumalaWoodshaper copy() {
        return new SumalaWoodshaper(this);
    }
}
