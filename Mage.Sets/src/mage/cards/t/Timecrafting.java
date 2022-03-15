
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
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
public final class Timecrafting extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");
    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    public Timecrafting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");

        // Choose one - Remove X time counters from target permanent or suspended card;
        this.getSpellAbility().addEffect(new TimecraftingRemoveEffect());
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard());

        // or put X time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode(new TimecraftingAddEffect());
        mode.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        this.getSpellAbility().addMode(mode);
    }

    private Timecrafting(final Timecrafting card) {
        super(card);
    }

    @Override
    public Timecrafting copy() {
        return new Timecrafting(this);
    }
}

class TimecraftingRemoveEffect extends OneShotEffect {

    TimecraftingRemoveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove X time counters from target permanent or suspended card";
    }

    TimecraftingRemoveEffect(final TimecraftingRemoveEffect effect) {
        super(effect);
    }

    @Override
    public TimecraftingRemoveEffect copy() {
        return new TimecraftingRemoveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = source.getManaCostsToPay().getX();
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.removeCounters(CounterType.TIME.createInstance(xValue), source, game);
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    card.removeCounters(CounterType.TIME.createInstance(xValue), source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class TimecraftingAddEffect extends OneShotEffect {

    TimecraftingAddEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put X time counters on target permanent with a time counter on it or suspended card";
    }

    TimecraftingAddEffect(final TimecraftingAddEffect effect) {
        super(effect);
    }

    @Override
    public TimecraftingAddEffect copy() {
        return new TimecraftingAddEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = source.getManaCostsToPay().getX();
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.addCounters(CounterType.TIME.createInstance(xValue), source.getControllerId(), source, game);
            }
            else {
                Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
                if (card != null) {
                    card.addCounters(CounterType.TIME.createInstance(xValue), source.getControllerId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
