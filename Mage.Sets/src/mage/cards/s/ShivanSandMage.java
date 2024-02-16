package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPermanentOrSuspendedCard;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ShivanSandMage extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter
            = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");

    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    public ShivanSandMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Shivan Sand-Mage enters the battlefield, choose one —
        // Remove two time counters from target permanent or suspended card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ShivanSandMageEffect(false));
        ability.addTarget(new TargetPermanentOrSuspendedCard());

        // Put two time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode(new ShivanSandMageEffect(true));
        mode.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        ability.addMode(mode);
        ability.getModes().addMode(mode);
        this.addAbility(ability);

        // Suspend 4-{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{R}"), this));
    }

    private ShivanSandMage(final ShivanSandMage card) {
        super(card);
    }

    @Override
    public ShivanSandMage copy() {
        return new ShivanSandMage(this);
    }
}

class ShivanSandMageEffect extends OneShotEffect {

    private final boolean addCounters;

    ShivanSandMageEffect(boolean addCounters) {
        super(Outcome.Benefit);
        this.addCounters = addCounters;
        if (addCounters) {
            this.staticText = "put two time counters on target permanent with a time counter on it or suspended card";
        } else {
            this.staticText = "remove two time counters from target permanent or suspended card";
        }
    }

    private ShivanSandMageEffect(final ShivanSandMageEffect effect) {
        super(effect);
        this.addCounters = effect.addCounters;
    }

    @Override
    public ShivanSandMageEffect copy() {
        return new ShivanSandMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (addCounters) {
                permanent.addCounters(CounterType.TIME.createInstance(2), source.getControllerId(), source, game);
            } else {
                permanent.removeCounters(CounterType.TIME.getName(), 2, source, game);
            }
            return true;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            if (addCounters) {
                card.addCounters(CounterType.TIME.createInstance(2), source.getControllerId(), source, game);
            } else {
                card.removeCounters(CounterType.TIME.getName(), 2, source, game);
            }
            return true;
        }
        return false;
    }
}
