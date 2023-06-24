package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.command.emblems.BasriKetEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class BasriKet extends CardImpl {

    public BasriKet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BASRI);
        this.setStartingLoyalty(3);

        // +1: Put a +1/+1 counter on up to one target creature. It gains indestructible until end of turn.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on up to one target creature"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains indestructible until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −2: Whenever one or more nontoken creatures attack this turn, create that many 1/1 white Soldier creature tokens that are tapped and attacking.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new BasriKetTriggeredAbility()), -2));

        // −6: You get an emblem with "At the beginning of combat on your turn, create a 1/1 white Soldier creature token, then put a +1/+1 counter on each creature you control."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new BasriKetEmblem()), -6));
    }

    private BasriKet(final BasriKet card) {
        super(card);
    }

    @Override
    public BasriKet copy() {
        return new BasriKet(this);
    }
}

class BasriKetTriggeredAbility extends DelayedTriggeredAbility {

    public BasriKetTriggeredAbility() {
        super(null, Duration.EndOfTurn, false);
    }

    public BasriKetTriggeredAbility(BasriKetTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BasriKetTriggeredAbility copy() {
        return new BasriKetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackingNonTokens = 0;
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent permanent = game.getPermanent(attacker);
            if (StaticFilters.FILTER_CREATURE_NON_TOKEN.match(permanent, game)) {
                attackingNonTokens++;
            }
        }
        if (attackingNonTokens > 0) {
            this.getEffects().clear();
            addEffect(new CreateTokenEffect(new SoldierToken(), attackingNonTokens, true, true));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more nontoken creatures attack this turn, create that many 1/1 white Soldier creature tokens that are tapped and attacking.";
    }
}