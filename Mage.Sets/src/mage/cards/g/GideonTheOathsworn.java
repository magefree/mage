package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonTheOathsworn extends CardImpl {

    public GideonTheOathsworn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);
        this.setStartingLoyalty(4);

        // Whenever you attack with two or more non-Gideon creatures, put a +1/+1 counter on each of those creatures.
        this.addAbility(new GideonTheOathswornTriggeredAbility());

        // +2: Until end of turn, Gideon, the Oathsworn becomes a 5/5 white Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        Ability ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(
                new GideonTheOathswornToken(), CardType.PLANESWALKER, Duration.EndOfTurn
        ), 2);
        ability.addEffect(new PreventAllDamageToSourceEffect(
                Duration.EndOfTurn
        ).setText("Prevent all damage that would be dealt to him this turn"));
        this.addAbility(ability);

        // -9: Exile Gideon, the Oathsworn and each creature your opponents control.
        ability = new LoyaltyAbility(new ExileSourceEffect(), -9);
        ability.addEffect(new ExileAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("and each creature your opponents control"));
        this.addAbility(ability);
    }

    private GideonTheOathsworn(final GideonTheOathsworn card) {
        super(card);
    }

    @Override
    public GideonTheOathsworn copy() {
        return new GideonTheOathsworn(this);
    }
}

class GideonTheOathswornTriggeredAbility extends TriggeredAbilityImpl {

    GideonTheOathswornTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private GideonTheOathswornTriggeredAbility(final GideonTheOathswornTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GideonTheOathswornTriggeredAbility copy() {
        return new GideonTheOathswornTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getCombat().getAttackingPlayerId().equals(getControllerId())) {
            int attackerCount = 0;
            Set<MageObjectReference> attackers = new HashSet();
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent permanent = game.getPermanent(attackerId);
                if (permanent != null && permanent.isCreature(game) && !permanent.hasSubtype(SubType.GIDEON, game)) {
                    attackerCount++;
                    attackers.add(new MageObjectReference(permanent, game));
                }
            }
            if (attackerCount >= 2) {
                this.getEffects().clear();
                this.addEffect(new GideonTheOathswornEffect(attackers));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you attack with two or more non-Gideon creatures, " +
                "put a +1/+1 counter on each of those creatures.";
    }
}

class GideonTheOathswornEffect extends OneShotEffect {

    private final Set<MageObjectReference> attackers;

    GideonTheOathswornEffect(Set<MageObjectReference> attackers) {
        super(Outcome.Benefit);
        this.attackers = attackers;
    }

    private GideonTheOathswornEffect(final GideonTheOathswornEffect effect) {
        super(effect);
        this.attackers = effect.attackers;
    }

    @Override
    public GideonTheOathswornEffect copy() {
        return new GideonTheOathswornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mor : attackers) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}

class GideonTheOathswornToken extends TokenImpl {

    GideonTheOathswornToken() {
        super("", "5/5 white Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    private GideonTheOathswornToken(final GideonTheOathswornToken token) {
        super(token);
    }

    @Override
    public GideonTheOathswornToken copy() {
        return new GideonTheOathswornToken(this);
    }
}
