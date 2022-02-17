
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class CytoplastRootKin extends CardImpl {

    public CytoplastRootKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 4
        this.addAbility(new GraftAbility(this, 4));
        
        // When Cytoplast Root-Kin enters the battlefield, put a +1/+1 counter on each other creature you control with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1)));
        
        // {2}: Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CytoplastRootKinEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private CytoplastRootKin(final CytoplastRootKin card) {
        super(card);
    }

    @Override
    public CytoplastRootKin copy() {
        return new CytoplastRootKin(this);
    }
}

class CytoplastRootKinEffect extends OneShotEffect {

    CytoplastRootKinEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin";
    }

    CytoplastRootKinEffect(final CytoplastRootKinEffect effect) {
        super(effect);
    }

    @Override
    public CytoplastRootKinEffect copy() {
        return new CytoplastRootKinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (sourcePermanent != null
                && targetPermanent != null
                && !sourcePermanent.getId().equals(targetPermanent.getId())
                && targetPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            targetPermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
            sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
