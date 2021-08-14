package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SemestersEnd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creatures and/or planeswalkers you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SemestersEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Exile any number of target creatures and/or planeswalkers you control. At the beginning of the next end step, return each of them to the battlefield under its owner's control. Each of them enters the battlefield with an additional +1/+1 counter on it if it's a creature and an additional loyalty counter on it if it's a planeswalker.
        this.getSpellAbility().addEffect(new SemestersEndEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
    }

    private SemestersEnd(final SemestersEnd card) {
        super(card);
    }

    @Override
    public SemestersEnd copy() {
        return new SemestersEnd(this);
    }
}

class SemestersEndEffect extends OneShotEffect {

    SemestersEndEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of target creatures and/or planeswalkers you control. " +
                "At the beginning of the next end step, return each of them to the battlefield " +
                "under its owner's control. Each of them enters the battlefield with an additional " +
                "+1/+1 counter on it if it's a creature and an additional loyalty counter on it if it's a planeswalker";
    }

    private SemestersEndEffect(final SemestersEndEffect effect) {
        super(effect);
    }

    @Override
    public SemestersEndEffect copy() {
        return new SemestersEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPointer.getTargets(game, source));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        game.addDelayedTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SemestersEndReturnEffect(cards, game)), source
        );
        return true;
    }
}

class SemestersEndReturnEffect extends OneShotEffect {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    SemestersEndReturnEffect(Cards cards, Game game) {
        super(Outcome.Benefit);
        staticText = "return the exiled card to the battlefield";
        cards.stream().map(uuid -> new MageObjectReference(uuid, game)).forEach(morSet::add);
    }

    private SemestersEndReturnEffect(final SemestersEndReturnEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public SemestersEndReturnEffect copy() {
        return new SemestersEndReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        morSet.stream().map(mor -> mor.getCard(game)).forEach(cards::add);
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
        cards.retainZone(Zone.BATTLEFIELD, game);
        if (cards.isEmpty()) {
            return false;
        }
        cards.stream().map(game::getPermanent).filter(Objects::nonNull).forEach(p -> {
            if (p.isCreature(game)) {
                p.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
            if (p.isPlaneswalker(game)) {
                p.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
            }
        });
        return true;
    }
}
