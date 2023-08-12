
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author fireshoes
 */
public final class KalitasTraitorOfGhet extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Vampire or Zombie");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(SubType.VAMPIRE.getPredicate(),
                (SubType.ZOMBIE.getPredicate())));
    }

    public KalitasTraitorOfGhet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If a nontoken creature an opponent controls would die, instead exile that card and create a 2/2 black Zombie creature token.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KalitasTraitorOfGhetEffect()));

        // {2}{B}, Sacrifice another Vampire or Zombie: Put two +1/+1 counters on Kalitas, Traitor of Ghet.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    private KalitasTraitorOfGhet(final KalitasTraitorOfGhet card) {
        super(card);
    }

    @Override
    public KalitasTraitorOfGhet copy() {
        return new KalitasTraitorOfGhet(this);
    }
}

class KalitasTraitorOfGhetEffect extends ReplacementEffectImpl {

    public KalitasTraitorOfGhetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a nontoken creature an opponent controls would die, instead exile that card and create a 2/2 black Zombie creature token";
    }

    public KalitasTraitorOfGhetEffect(final KalitasTraitorOfGhetEffect effect) {
        super(effect);
    }

    @Override
    public KalitasTraitorOfGhetEffect copy() {
        return new KalitasTraitorOfGhetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    controller.moveCards(permanent, Zone.EXILED, source, game);
                    new CreateTokenEffect(new ZombieToken()).apply(game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId()) && !(permanent instanceof PermanentToken)) {
                if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
                    if (zEvent.getTarget().isCreature(game)) {
                        return true;
                    }
                } else if (permanent.isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
