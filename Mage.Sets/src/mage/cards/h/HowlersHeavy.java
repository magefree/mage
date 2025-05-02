package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlersHeavy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HowlersHeavy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SEAL);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Cycling {1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{U}")));

        // When you cycle this card, target creature or Vehicle an opponent controls gets -3/-0 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostTargetEffect(-3, 0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HowlersHeavy(final HowlersHeavy card) {
        super(card);
    }

    @Override
    public HowlersHeavy copy() {
        return new HowlersHeavy(this);
    }
}
