
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DaghatarTheAdamant extends CardImpl {

    public DaghatarTheAdamant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Daghatar the Adamant enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                "with four +1/+1 counters on it"));

        // {1}{B/G}{B/G}: Move a +1/+1 counter from target creature onto a second target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveCounterFromTargetToTargetEffect(),new ManaCostsImpl<>("{1}{B/G}{B/G}"));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature the +1/+1 counter is moved from")));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature the +1/+1 counter is moved to")));
        this.addAbility(ability);


    }

    private DaghatarTheAdamant(final DaghatarTheAdamant card) {
        super(card);
    }

    @Override
    public DaghatarTheAdamant copy() {
        return new DaghatarTheAdamant(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect {

    public MoveCounterFromTargetToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from target creature onto a second target creature";
    }

    public MoveCounterFromTargetToTargetEffect(final MoveCounterFromTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToTargetEffect copy() {
        return new MoveCounterFromTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (fromPermanent != null && fromPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (toPermanent != null) {
                    fromPermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
                    toPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    game.informPlayers(sourceObject.getLogName() + ": Moved a +1/+1 counter from " + fromPermanent.getLogName() +" to " + toPermanent.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
