package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmileAtDeath extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature cards with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SmileAtDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // At the beginning of your upkeep, return up to two target creature cards with power 2 or less from your graveyard to the battlefield. Put a +1/+1 counter on each of those creatures.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SmileAtDeathEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.addAbility(ability);
    }

    private SmileAtDeath(final SmileAtDeath card) {
        super(card);
    }

    @Override
    public SmileAtDeath copy() {
        return new SmileAtDeath(this);
    }
}

class SmileAtDeathEffect extends OneShotEffect {

    SmileAtDeathEffect() {
        super(Outcome.Benefit);
        staticText = "return up to two target creature cards with power 2 or less " +
                "from your graveyard to the battlefield. Put a +1/+1 counter on each of those creatures";
    }

    private SmileAtDeathEffect(final SmileAtDeathEffect effect) {
        super(effect);
    }

    @Override
    public SmileAtDeathEffect copy() {
        return new SmileAtDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (UUID cardId : cards) {
            Optional.ofNullable(cardId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}
