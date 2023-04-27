
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ThopterSquadron extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Thopter");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.THOPTER.getPredicate());
    }

    public ThopterSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Thopter Squadron enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // {1}, Remove a +1/+1 counter from Thopter Squadron: Create a 1/1 colorless Thopter artifact creature token with flying. Activate this secondAbility only any time you could cast a sorcery.
        Ability firstAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken(), 1), new GenericManaCost(1));
        firstAbility.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(firstAbility);

        // {1}, Sacrifice another Thopter: Put a +1/+1 counter on Thopter Squadron. Activate this secondAbility only any time you could cast a sorcery.
        Ability secondAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new ManaCostsImpl<>("{1}"));
        secondAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(secondAbility);
    }

    private ThopterSquadron(final ThopterSquadron card) {
        super(card);
    }

    @Override
    public ThopterSquadron copy() {
        return new ThopterSquadron(this);
    }
}
