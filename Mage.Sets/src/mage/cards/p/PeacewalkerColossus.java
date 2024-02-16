
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class PeacewalkerColossus extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("another target vehicle");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.VEHICLE.getPredicate());
    }

    public PeacewalkerColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {1}{W}: Another target Vehicle you control becomes an artifact creature until end of turn.
        Effect effect = new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.CREATURE);
        effect.setText("Another target Vehicle you control becomes an artifact creature until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

        // Crew 4 <i>(Tap any number of creatures you control with total power 4 or more: This Vehicle becomes an artifact creature until end of turn.)
        this.addAbility(new CrewAbility(4));
    }

    private PeacewalkerColossus(final PeacewalkerColossus card) {
        super(card);
    }

    @Override
    public PeacewalkerColossus copy() {
        return new PeacewalkerColossus(this);
    }
}
