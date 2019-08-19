
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author LevelX2
 */
public final class RiftElemental extends CardImpl {

    public RiftElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}, Remove a time counter from a permanent you control or suspended card you own: Rift Elemental gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new RiftElementalCost());
        this.addAbility(ability);
    }

    public RiftElemental(final RiftElemental card) {
        super(card);
    }

    @Override
    public RiftElemental copy() {
        return new RiftElemental(this);
    }
}

class RiftElementalCost extends CostImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent you control with a time counter or suspended card you own");
    static {
        filter.getPermanentFilter().add(new ControllerPredicate(TargetController.YOU));
        filter.getPermanentFilter().add(new CounterPredicate(CounterType.TIME));
        filter.getCardFilter().add(new OwnerPredicate(TargetController.YOU));
    }

    RiftElementalCost() {
        text = "Remove a time counter from a permanent you control or suspended card you own";
    }

    RiftElementalCost(final RiftElementalCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Target target = new TargetPermanentOrSuspendedCard(filter, true);
            if (target.choose(Outcome.Neutral, controllerId, sourceId, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.removeCounters(CounterType.TIME.createInstance(), game);
                    this.paid = true;
                }
                else {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        card.removeCounters(CounterType.TIME.createInstance(), game);
                        this.paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Target target = new TargetPermanentOrSuspendedCard(filter, true);
        return target.hasPossibleTargets(sourceId, controllerId, game);
    }

    @Override
    public RiftElementalCost copy() {
        return new RiftElementalCost(this);
    }
}
