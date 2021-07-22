
package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class ZombieTrailblazer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Zombie you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ZombieTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Zombie you control: Target land becomes a Swamp until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.SWAMP), new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // Tap an untapped Zombie you control: Target creature gains swampwalk until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(new SwampwalkAbility(false), Duration.EndOfTurn), new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ZombieTrailblazer(final ZombieTrailblazer card) {
        super(card);
    }

    @Override
    public ZombieTrailblazer copy() {
        return new ZombieTrailblazer(this);
    }
}
