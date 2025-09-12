package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ForerunnerOfTheLegion extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.VAMPIRE);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.VAMPIRE, "another Vampire you control");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public ForerunnerOfTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Forerunner of the Legion enters the battlefield, you may search your library for a Vampire card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever another Vampire you control enters, target creature gets +1/+1 until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new BoostTargetEffect(1, 1), filter2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ForerunnerOfTheLegion(final ForerunnerOfTheLegion card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheLegion copy() {
        return new ForerunnerOfTheLegion(this);
    }
}
