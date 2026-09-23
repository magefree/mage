package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SandWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author brahle
 */
public final class DesertWarfare extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DESERT, "you control five or more Deserts");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4, false);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.DESERT));
    private static final Hint hint = new ValueHint("Deserts you control", xValue);

    public DesertWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step.
        this.addAbility(new DesertWarfareTriggeredAbility());

        // At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DesertWarfareEffect()
        ).withInterveningIf(condition).addHint(hint));
    }

    private DesertWarfare(final DesertWarfare card) {
        super(card);
    }

    @Override
    public DesertWarfare copy() {
        return new DesertWarfare(this);
    }

    static int getDesertCount(Game game, Ability source, Effect effect) {
        return xValue.calculate(game, source, effect);
    }
}

class DesertWarfareTriggeredAbility extends TriggeredAbilityImpl {

    DesertWarfareTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new ReturnToBattlefieldUnderYourControlTargetEffect(),
                        TargetController.YOU
                )
        ).setText("put that card onto the battlefield under your control at the beginning of your next end step"));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, ");
    }

    private DesertWarfareTriggeredAbility(final DesertWarfareTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DesertWarfareTriggeredAbility copy() {
        return new DesertWarfareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case SACRIFICED_PERMANENT:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent == null || !permanent.hasSubtype(SubType.DESERT, game)) {
                    return false;
                }
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getToZone() != Zone.GRAVEYARD
                        || (zEvent.getFromZone() != Zone.HAND && zEvent.getFromZone() != Zone.LIBRARY)) {
                    return false;
                }
                Card card = game.getCard(zEvent.getTargetId());
                if (card == null || !card.isOwnedBy(getControllerId()) || !card.hasSubtype(SubType.DESERT, game)) {
                    return false;
                }
                this.getEffects().setTargetPointer(new FixedTarget(zEvent.getTargetId(), game));
                return true;
            default:
                return false;
        }
    }
}

class DesertWarfareEffect extends OneShotEffect {

    DesertWarfareEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste";
    }

    private DesertWarfareEffect(final DesertWarfareEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareEffect copy() {
        return new DesertWarfareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = DesertWarfare.getDesertCount(game, source, this);
        if (count < 1) {
            return false;
        }
        Token token = new SandWarriorToken();
        token.putOntoBattlefield(count, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
