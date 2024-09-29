package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfBurningAlive extends CardImpl {

    public FearOfBurningAlive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Fear of Burning Alive enters, it deals 4 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(4, TargetController.OPPONENT, "it")
        ));

        // Delirium -- Whenever a source you control deals noncombat damage to an opponent, if there are four or more card types among cards in your graveyard, Fear of Burning Alive deals that amount of damage to target creature that player controls.
        this.addAbility(new FearOfBurningAliveTriggeredAbility());
    }

    private FearOfBurningAlive(final FearOfBurningAlive card) {
        super(card);
    }

    @Override
    public FearOfBurningAlive copy() {
        return new FearOfBurningAlive(this);
    }
}

class FearOfBurningAliveTriggeredAbility extends TriggeredAbilityImpl {

    FearOfBurningAliveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MANY)
                .setText("{this} deals that amount of damage to target creature that player controls"));
        this.setTriggerPhrase("Whenever a source you control deals noncombat damage to an opponent, " +
                "if there are four or more card types among cards in your graveyard, ");
        this.setAbilityWord(AbilityWord.DELIRIUM);
        this.addHint(CardTypesInGraveyardHint.YOU);
    }

    private FearOfBurningAliveTriggeredAbility(final FearOfBurningAliveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FearOfBurningAliveTriggeredAbility copy() {
        return new FearOfBurningAliveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()
                || !isControlledBy(game.getControllerId(event.getSourceId()))
                || !game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(event.getTargetId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return DeliriumCondition.instance.apply(game, this);
    }
}
