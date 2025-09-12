package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpecimenFreighter extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Spacecraft creatures");

    static {
        filter.add(Predicates.not(SubType.SPACECRAFT.getPredicate()));
    }

    public SpecimenFreighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, return up to two target non-Spacecraft creatures to their owners' hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 9+
        // Flying
        // Whenever this Spacecraft attacks, defending player mills four cards.
        // 4/7
        this.addAbility(new StationLevelAbility(9)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new AttacksTriggeredAbility(
                        new MillCardsTargetEffect(4)
                                .setText("defending player mills four cards"),
                        false, null, SetTargetPointer.PLAYER
                ))
                .withPT(4, 7));
    }

    private SpecimenFreighter(final SpecimenFreighter card) {
        super(card);
    }

    @Override
    public SpecimenFreighter copy() {
        return new SpecimenFreighter(this);
    }
}
