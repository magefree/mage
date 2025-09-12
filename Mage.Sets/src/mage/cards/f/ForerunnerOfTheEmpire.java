package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ForerunnerOfTheEmpire extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.DINOSAUR);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.DINOSAUR, "Dinosaur you control");

    public ForerunnerOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Forerunner of the Empire enters the battlefield, you may search your library for a Dinosaur card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever a Dinosaur you control enters, you may have Forerunner of the Empire deal 1 damage to each creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE)
                        .setText("have {this} deal 1 damage to each creature"),
                filter2, true
        ));
    }

    private ForerunnerOfTheEmpire(final ForerunnerOfTheEmpire card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheEmpire copy() {
        return new ForerunnerOfTheEmpire(this);
    }
}
