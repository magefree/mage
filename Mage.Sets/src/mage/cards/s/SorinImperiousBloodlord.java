package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class SorinImperiousBloodlord extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "a Vampire");
    private static final FilterCard filter2
            = new FilterCreatureCard("a Vampire creature card");

    static {
        filter2.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public SorinImperiousBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Target creature you control gains deathtouch and lifelink until end of turn. If it's a Vampire, put a +1/+1 counter on it.
        Ability ability = new LoyaltyAbility(new SorinImperiousBloodlordEffect(), 1);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // +1: You may sacrifice a Vampire. When you do, Sorin, Imperious Bloodlord deals 3 damage to any target and you gain 3 life.
        this.addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new SorinImperiousBloodlordCreateReflexiveTriggerEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ).setText("You may sacrifice a Vampire. " +
                "When you do, {this} deals 3 damage to any target and you gain 3 life."
        ), 1));

        // −3: You may put a Vampire creature card from your hand onto the battlefield.
        this.addAbility(new LoyaltyAbility(new PutCardFromHandOntoBattlefieldEffect(filter2), -3));
    }

    private SorinImperiousBloodlord(final SorinImperiousBloodlord card) {
        super(card);
    }

    @Override
    public SorinImperiousBloodlord copy() {
        return new SorinImperiousBloodlord(this);
    }
}

class SorinImperiousBloodlordEffect extends OneShotEffect {

    SorinImperiousBloodlordEffect() {
        super(Benefit);
        staticText = "Target creature you control gains deathtouch and lifelink until end of turn. " +
                "If it's a Vampire, put a +1/+1 counter on it.";
    }

    private SorinImperiousBloodlordEffect(final SorinImperiousBloodlordEffect effect) {
        super(effect);
    }

    @Override
    public SorinImperiousBloodlordEffect copy() {
        return new SorinImperiousBloodlordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.hasSubtype(SubType.VAMPIRE, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        game.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), source);
        game.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}

class SorinImperiousBloodlordCreateReflexiveTriggerEffect extends OneShotEffect {

    SorinImperiousBloodlordCreateReflexiveTriggerEffect() {
        super(Benefit);
    }

    private SorinImperiousBloodlordCreateReflexiveTriggerEffect(final SorinImperiousBloodlordCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SorinImperiousBloodlordCreateReflexiveTriggerEffect copy() {
        return new SorinImperiousBloodlordCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new SorinImperiousBloodlordReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class SorinImperiousBloodlordReflexiveTriggeredAbility extends DelayedTriggeredAbility {
    SorinImperiousBloodlordReflexiveTriggeredAbility() {
        super(new DamageTargetEffect(3), Duration.OneUse, true);
        this.addEffect(new GainLifeEffect(3));
        this.addTarget(new TargetAnyTarget());
    }

    private SorinImperiousBloodlordReflexiveTriggeredAbility(final SorinImperiousBloodlordReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SorinImperiousBloodlordReflexiveTriggeredAbility copy() {
        return new SorinImperiousBloodlordReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "{this} deals 3 damage to any target and you gain 3 life";
    }
}
