
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPermanentOrSuspendedCard;

/**
 *
 * @author L_J
 */
public final class Timebender extends CardImpl {

    public Timebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{U}")));

        // When Timebender is turned face up, choose one — 
        // Remove two time counters from target permanent or suspended card.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new TimebenderEffect(false));
        ability.addTarget(new TargetPermanentOrSuspendedCard());

        // Put two time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode();
        mode.getEffects().add(new TimebenderEffect(true));
        mode.getTargets().add(new TargetPermanentOrSuspendedCard());
        ability.addMode(mode);
        ability.getModes().addMode(mode);
        this.addAbility(ability);

    }

    public Timebender(final Timebender card) {
        super(card);
    }

    @Override
    public Timebender copy() {
        return new Timebender(this);
    }
}

class TimebenderEffect extends OneShotEffect {
    
    private final boolean addCounters;

    public TimebenderEffect(boolean addCounters) {
        super(Outcome.Benefit);
        this.addCounters = addCounters;
        if (addCounters) {
            this.staticText = "put two time counters on target permanent or suspended card";
        } else {
            this.staticText = "remove two time counters from target permanent or suspended card";
        }
    }

    public TimebenderEffect(final TimebenderEffect effect) {
        super(effect);
        this.addCounters = effect.addCounters;
    }

    @Override
    public TimebenderEffect copy() {
        return new TimebenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (addCounters) {
                permanent.addCounters(CounterType.TIME.createInstance(2), source, game);
            } else {
                permanent.removeCounters(CounterType.TIME.getName(), 2, game);
            }
            return true;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            if (addCounters) {
                card.addCounters(CounterType.TIME.createInstance(2), source, game);
            } else {
                card.removeCounters(CounterType.TIME.getName(), 2, game);
            }
            return true;
        }
        return false;
    }
}
