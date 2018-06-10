
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class DesecrationDemon extends CardImpl {

    public DesecrationDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.DEMON);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each combat, any opponent may sacrifice a creature. If a player does, tap Desecration Demon and put a +1/+1 counter on it.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DesecrationDemonEffect(), TargetController.ANY, false));
    }

    public DesecrationDemon(final DesecrationDemon card) {
        super(card);
    }

    @Override
    public DesecrationDemon copy() {
        return new DesecrationDemon(this);
    }
}

class DesecrationDemonEffect extends OneShotEffect {
    DesecrationDemonEffect() {
        super(Outcome.BoostCreature);
        staticText = "any opponent may sacrifice a creature. If a player does, tap {this} and put a +1/+1 counter on it";
    }

    DesecrationDemonEffect(final DesecrationDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent descrationDemon = game.getPermanent(source.getSourceId());
        if (controller != null && descrationDemon != null) {
            for (UUID opponentId: game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterControlledPermanent filter = new FilterControlledPermanent("creature to sacrifice");
                    filter.add(new CardTypePredicate(CardType.CREATURE));
                    filter.add(new ControllerPredicate(TargetController.YOU));
                    TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);
                    if (target.canChoose(opponent.getId(), game)) {
                        if (opponent.chooseUse(Outcome.AIDontUseIt, "Sacrifice a creature to tap " + descrationDemon.getLogName() + "and put a +1/+1 counter on it?", source, game))
                        {
                            opponent.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                                game.informPlayers(opponent.getLogName() + " sacrifices " + permanent.getLogName() + " to tap " + descrationDemon.getLogName() + ". A +1/+1 counter was put on it");
                                descrationDemon.tap(game);
                                descrationDemon.addCounters(CounterType.P1P1.createInstance(), source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DesecrationDemonEffect copy() {
        return new DesecrationDemonEffect(this);
    }
}
