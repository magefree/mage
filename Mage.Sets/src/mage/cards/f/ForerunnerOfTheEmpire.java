
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author JayDi85
 */
public final class ForerunnerOfTheEmpire extends CardImpl {

    private static final FilterCreaturePermanent filterAnyDinosaur = new FilterCreaturePermanent(SubType.DINOSAUR, SubType.DINOSAUR.toString());

    static {
        filterAnyDinosaur.add(new ControllerPredicate(TargetController.YOU));
    }

    public ForerunnerOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Forerunner of the Empire enters the battlefield, you may search your library for a Dinosaur card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryPutOnLibraryEffect(
                                new TargetCardInLibrary(new FilterBySubtypeCard(SubType.DINOSAUR)),
                                true,
                                true
                        ),
                        true
                )
        );

        // Whenever a Dinosaur enters the battlefield under your control, you may have Forerunner of the Empire deal 1 damage to each creature.
        Ability ability = new CreatureEntersBattlefieldTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageAllEffect(1, new FilterCreaturePermanent()).setText("have {this} deal 1 damage to each creature"),
                filterAnyDinosaur,
                true,
                false);
        this.addAbility(ability);
    }

    public ForerunnerOfTheEmpire(final ForerunnerOfTheEmpire card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheEmpire copy() {
        return new ForerunnerOfTheEmpire(this);
    }
}
