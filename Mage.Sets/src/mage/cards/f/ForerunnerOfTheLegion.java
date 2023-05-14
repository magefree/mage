
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JayDi85
 */
public final class ForerunnerOfTheLegion extends CardImpl {

    private static final FilterPermanent filterAnotherVampire = new FilterPermanent(SubType.VAMPIRE, "another " + SubType.VAMPIRE.toString());
    static {
        filterAnotherVampire.add(AnotherPredicate.instance);
        filterAnotherVampire.add(TargetController.YOU.getControllerPredicate());
    }

    public ForerunnerOfTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Forerunner of the Legion enters the battlefield, you may search your library for a Vampire card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryPutOnLibraryEffect(
                                new TargetCardInLibrary(new FilterBySubtypeCard(SubType.VAMPIRE)),
                                true
                        ),
                        true
                )
        );

        // Whenever another Vampire enters the battlefield under your control, target creature gets +1/+1 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoostTargetEffect(1,1, Duration.EndOfTurn), filterAnotherVampire);
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
