package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.DefendingPlayerControlsNoSourceCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile, Susucr
 */
public final class KjeldoranGuard extends CardImpl {

    private static final FilterLandPermanent snowLandFiler = new FilterLandPermanent("a snow land");

    static { snowLandFiler.add(SuperType.SNOW.getPredicate()); }

    public KjeldoranGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target creature gets +1/+1 until end of turn. When that creature leaves the battlefield this turn, sacrifice Kjeldoran Guard. Activate only during combat and only if defending player controls no snow lands.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new KjeldoranGuardEffect(),
                new TapSourceCost(),
                new DefendingPlayerControlsNoSourceCondition(snowLandFiler),
                "activate only during combat and only if defending player controls no snow lands"
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KjeldoranGuard(final KjeldoranGuard card) { super(card); }

    @Override
    public Card copy() {
        return new KjeldoranGuard(this);
    }
}

class KjeldoranGuardEffect extends OneShotEffect {

    KjeldoranGuardEffect() {
        super(Outcome.Neutral);
        staticText = "Target creature gets +1/+1 until end of turn." +
                "When that creature leaves the battlefield this turn, sacrifice {this}." +
                "Activate only during combat and only if defending player controls no snow lands.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        if (game.getPermanent(targetId) == null) {
            return false;
        }

        // Target creature gets +1/+1 until end of turn.
        BoostTargetEffect buffEffect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        buffEffect.setTargetPointer(new FixedTarget(targetId, game));
        game.addEffect(buffEffect, source);

        // When that creature leaves the battlefield this turn, sacrifice Kjeldoran Guard.
        game.addDelayedTriggeredAbility(
                new KjeldoranGuardDelayedTriggeredAbility(targetId),
                source
        );

        return true;
    }

    private KjeldoranGuardEffect(KjeldoranGuardEffect effect) { super(effect); }

    @Override
    public KjeldoranGuardEffect copy() { return new KjeldoranGuardEffect(this); }
}

class KjeldoranGuardDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID creatureId;

    KjeldoranGuardDelayedTriggeredAbility(UUID creatureId) {
        super(new SacrificeSourceEffect(), Duration.EndOfTurn, true);
        this.creatureId = creatureId;
    }

    KjeldoranGuardDelayedTriggeredAbility(KjeldoranGuardDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureId = ability.creatureId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(creatureId);
    }

    @Override
    public KjeldoranGuardDelayedTriggeredAbility copy() {
        return new KjeldoranGuardDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() { return "sacrifice {this}"; }
}