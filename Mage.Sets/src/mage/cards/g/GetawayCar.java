package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CrewedSourceThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.CrewedVehicleWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GetawayCar extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that crewed it this turn");

    static {
        filter.add(CrewedSourceThisTurnPredicate.instance);
    }

    public GetawayCar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Getaway Car attacks or blocks, return up to one target creature that crewed it this turn to its owner's hand.
        Ability ability = new AttacksOrBlocksTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability, new CrewedVehicleWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private GetawayCar(final GetawayCar card) {
        super(card);
    }

    @Override
    public GetawayCar copy() {
        return new GetawayCar(this);
    }
}
