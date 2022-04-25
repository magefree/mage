package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimeGorger extends CardImpl {

    public GrimeGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Grime Gorger attacks, exile up to one card of each card type from defending player's graveyard. Put a +1/+1 counter on Grime Gorger for each card exiled this way.
        this.addAbility(new AttacksTriggeredAbility(
                new GrimeGorgerEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private GrimeGorger(final GrimeGorger card) {
        super(card);
    }

    @Override
    public GrimeGorger copy() {
        return new GrimeGorger(this);
    }
}

class GrimeGorgerEffect extends OneShotEffect {

    GrimeGorgerEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one card of each card type from defending player's graveyard. " +
                "Put a +1/+1 on {this} for each card exiled this way";
    }

    private GrimeGorgerEffect(final GrimeGorgerEffect effect) {
        super(effect);
    }

    @Override
    public GrimeGorgerEffect copy() {
        return new GrimeGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        UUID defenderId = getTargetPointer().getFirst(game, source);
        if (player == null || defenderId == null) {
            return false;
        }
        TargetCardInGraveyard target = new GrimeGorgerTarget(defenderId);
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(cards.size()), source, game);
        }
        return true;
    }
}

class GrimeGorgerTarget extends TargetCardInGraveyard {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(CardType.values());

    GrimeGorgerTarget(UUID playerId) {
        super(0, Integer.MAX_VALUE, makeFilter(playerId));
        this.notTarget = true;
    }

    private GrimeGorgerTarget(final GrimeGorgerTarget target) {
        super(target);
    }

    @Override
    public GrimeGorgerTarget copy() {
        return new GrimeGorgerTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, null, game));
        return possibleTargets;
    }

    private static FilterCard makeFilter(UUID playerId) {
        FilterCard filter = new FilterCard("a card of each card type");
        filter.add(new OwnerIdPredicate(playerId));
        return filter;
    }
}
