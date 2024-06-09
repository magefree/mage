package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.abilities.keyword.CrewAbility;
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
public final class SubterraneanSchooner extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that crewed it this turn");

    static {
        filter.add(CrewedSourceThisTurnPredicate.instance);
    }

    public SubterraneanSchooner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Subterranean Schooner attacks, target creature that crewed it this turn explores.
        Ability ability = new AttacksTriggeredAbility(new ExploreTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new CrewedVehicleWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private SubterraneanSchooner(final SubterraneanSchooner card) {
        super(card);
    }

    @Override
    public SubterraneanSchooner copy() {
        return new SubterraneanSchooner(this);
    }
}
