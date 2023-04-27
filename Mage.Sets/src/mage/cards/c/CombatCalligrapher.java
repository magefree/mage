package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InklingToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombatCalligrapher extends CardImpl {

    public CombatCalligrapher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Inklings can't attack you or planeswalkers you control.
        this.addAbility(new SimpleStaticAbility(new CombatCalligrapherEffect()));

        // Whenever a player attacks one of your opponents, that attacking player creates a tapped 2/1 white and black Inkling creature token with flying that's attacking that opponent.
        this.addAbility(new CombatCalligrapherTriggeredAbility());
    }

    private CombatCalligrapher(final CombatCalligrapher card) {
        super(card);
    }

    @Override
    public CombatCalligrapher copy() {
        return new CombatCalligrapher(this);
    }
}

class CombatCalligrapherTriggeredAbility extends TriggeredAbilityImpl {

    CombatCalligrapherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(
                new InklingToken(), StaticValue.get(1), true, true
        ), false);
    }

    private CombatCalligrapherTriggeredAbility(final CombatCalligrapherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CombatCalligrapherTriggeredAbility copy() {
        return new CombatCalligrapherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player attacker = game.getPlayer(event.getPlayerId());
        Player defender = game.getPlayer(event.getTargetId());
        // Do not trigger if opponent is out of range (not visible as opponent to controller, and not the controller)
        // or if the person being attacked is not an opponent of the controller.
        if ((!game.getOpponents(getControllerId()).contains(attacker.getId()) && attacker.getId() != getControllerId())
                || !game.getOpponents(getControllerId()).contains(defender.getId())) {
            return false;
        }
        getEffects().setValue("playerToAttack", defender.getId());
        getEffects().setTargetPointer(new FixedTarget(attacker.getId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks one of your opponents, that attacking player creates " +
                "a tapped 2/1 white and black Inkling creature token with flying that's attacking that opponent.";
    }
}

class CombatCalligrapherEffect extends RestrictionEffect {

    public CombatCalligrapherEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Inklings can't attack you or planeswalkers you control";
    }

    public CombatCalligrapherEffect(final CombatCalligrapherEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.hasSubtype(SubType.INKLING, game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (source.isControlledBy(defenderId)) {
            return false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !planeswalker.isControlledBy(source.getControllerId());
    }


    @Override
    public CombatCalligrapherEffect copy() {
        return new CombatCalligrapherEffect(this);
    }
}
