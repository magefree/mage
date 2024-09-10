package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class LlanowarSentinel extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card named Llanowar Sentinel");

    static {
        filter.add(new NamePredicate("Llanowar Sentinel"));
    }

    public LlanowarSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Llanowar Sentinel enters the battlefield, you may pay {1}{G}. If you do, search your library for a card named Llanowar Sentinel and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true), new ManaCostsImpl<>("{1}{G}")
        )));
    }

    private LlanowarSentinel(final LlanowarSentinel card) {
        super(card);
    }

    @Override
    public LlanowarSentinel copy() {
        return new LlanowarSentinel(this);
    }
}
