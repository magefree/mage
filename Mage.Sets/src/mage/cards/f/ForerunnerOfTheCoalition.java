
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author JayDi85
 */
public final class ForerunnerOfTheCoalition extends CardImpl {

    private static final FilterPermanent filterAnotherPirate = new FilterPermanent(SubType.PIRATE, "another " + SubType.PIRATE.toString());

    static {
        filterAnotherPirate.add(AnotherPredicate.instance);
        filterAnotherPirate.add(TargetController.YOU.getControllerPredicate());
    }

    public ForerunnerOfTheCoalition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Forerunner of the Coalition enters the battlefield, you may search your library for a Pirate card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(
                        new TargetCardInLibrary(new FilterBySubtypeCard(SubType.PIRATE)),
                        true), true));

        // Whenever another Pirate enters the battlefield under your control, each opponent loses 1 life.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1),
                filterAnotherPirate, false);
        this.addAbility(ability);
    }

    private ForerunnerOfTheCoalition(final ForerunnerOfTheCoalition card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheCoalition copy() {
        return new ForerunnerOfTheCoalition(this);
    }
}
