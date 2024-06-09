
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author emerald000
 */
public final class JhoirasTimebug extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent you control or suspended card you own");
    static {
        filter.getPermanentFilter().add(TargetController.YOU.getControllerPredicate());
        filter.getCardFilter().add(TargetController.YOU.getOwnerPredicate());
    }

    public JhoirasTimebug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Choose target permanent you control or suspended card you own. If that permanent or card has a time counter on it, you may remove a time counter from it or put another time counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JhoirasTimebugEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        this.addAbility(ability);
    }

    private JhoirasTimebug(final JhoirasTimebug card) {
        super(card);
    }

    @Override
    public JhoirasTimebug copy() {
        return new JhoirasTimebug(this);
    }
}

class JhoirasTimebugEffect extends OneShotEffect {

    JhoirasTimebugEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target permanent you control or suspended card you own. If that permanent or card has a time counter on it, you may remove a time counter from it or put another time counter on it";
    }

    private JhoirasTimebugEffect(final JhoirasTimebugEffect effect) {
        super(effect);
    }

    @Override
    public JhoirasTimebugEffect copy() {
        return new JhoirasTimebugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null && permanent.getCounters(game).containsKey(CounterType.TIME)) {
                if (controller.chooseUse(Outcome.Benefit, "Add a time counter? (Otherwise remove one)", source, game)) {
                    permanent.addCounters(CounterType.TIME.createInstance(), source.getControllerId(), source, game);
                }
                else {
                    permanent.removeCounters(CounterType.TIME.createInstance(), source, game);
                }
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    if (controller.chooseUse(Outcome.Detriment, "Add a time counter? (Otherwise remove one)", source, game)) {
                        card.addCounters(CounterType.TIME.createInstance(), source.getControllerId(), source, game);
                    }
                    else {
                        card.removeCounters(CounterType.TIME.createInstance(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
