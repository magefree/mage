package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;
import mage.util.CardUtil;

/**
 * @author Cguy7777
 */
public final class TheRollercrusherRide extends CardImpl {

    public TheRollercrusherRide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // Delirium -- If a source you control would deal noncombat damage to a permanent or player while there are four or more card types among cards in your graveyard,
        // it deals double that damage instead.
        this.addAbility(new SimpleStaticAbility(new TheRollercrusherRideEffect())
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // When The Rollercrusher Ride enters, it deals X damage to each of up to X target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(GetXValue.instance)
                .setText("it deals X damage to each of up to X target creatures"));
        ability.addTarget(new TargetCreaturePermanent(0, 0));
        ability.setTargetAdjuster(new TargetsCountAdjuster(GetXValue.instance));
        this.addAbility(ability);
    }

    private TheRollercrusherRide(final TheRollercrusherRide card) {
        super(card);
    }

    @Override
    public TheRollercrusherRide copy() {
        return new TheRollercrusherRide(this);
    }
}

class TheRollercrusherRideEffect extends ReplacementEffectImpl {

    TheRollercrusherRideEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to a permanent or player " +
                "while there are four or more card types among cards in your graveyard, " +
                "it deals double that damage instead";
    }

    private TheRollercrusherRideEffect(final TheRollercrusherRideEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(game.getControllerId(event.getSourceId()))
                && !((DamageEvent) event).isCombatDamage()
                && DeliriumCondition.instance.apply(game, source);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public TheRollercrusherRideEffect copy() {
        return new TheRollercrusherRideEffect(this);
    }
}
