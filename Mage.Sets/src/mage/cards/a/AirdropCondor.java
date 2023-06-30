
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class AirdropCondor extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Goblin creature");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public AirdropCondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{R}, Sacrifice a Goblin creature: Airdrop Condor deals damage equal to the sacrificed creature's power to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(SacrificeCostCreaturesPower.instance)
                .setText("{this} deals damage equal to the sacrificed creature's power to any target"), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AirdropCondor(final AirdropCondor card) {
        super(card);
    }

    @Override
    public AirdropCondor copy() {
        return new AirdropCondor(this);
    }
}
