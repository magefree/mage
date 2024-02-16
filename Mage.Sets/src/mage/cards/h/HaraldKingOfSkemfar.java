package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaraldKingOfSkemfar extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elf, Warrior, or Tyvar card");

    static {
        filter.add(Predicates.or(
                SubType.ELF.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.TYVAR.getPredicate()
        ));
    }

    public HaraldKingOfSkemfar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Harald, King of Skemfar enters the battlefield, look at the top five cards of your library.
        // You may reveal an Elf, Warrior, or Tyvar card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)));
    }

    private HaraldKingOfSkemfar(final HaraldKingOfSkemfar card) {
        super(card);
    }

    @Override
    public HaraldKingOfSkemfar copy() {
        return new HaraldKingOfSkemfar(this);
    }
}
