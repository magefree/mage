package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZidaneTantalusThief extends CardImpl {

    public ZidaneTantalusThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Zidane enters, gain control of target creature an opponent controls until end of turn. Untap it. It gains lifelink and haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn));
        ability.addEffect(new UntapTargetEffect("untap it"));
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()).setText("It gains lifelink"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever an opponent gains control of a permanent from you, create a Treasure token.
        this.addAbility(new ZidaneTantalusThiefTriggeredAbility());
    }

    private ZidaneTantalusThief(final ZidaneTantalusThief card) {
        super(card);
    }

    @Override
    public ZidaneTantalusThief copy() {
        return new ZidaneTantalusThief(this);
    }
}

class ZidaneTantalusThiefTriggeredAbility extends TriggeredAbilityImpl {

    ZidaneTantalusThiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever an opponent gains control of a permanent from you, ");
    }

    private ZidaneTantalusThiefTriggeredAbility(final ZidaneTantalusThiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZidaneTantalusThiefTriggeredAbility copy() {
        return new ZidaneTantalusThiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && Optional
                .ofNullable(event.getTargetId())
                .map(game::getPermanent)
                .map(Controllable::getControllerId)
                .map(uuid -> game.getOpponents(getControllerId()).contains(uuid))
                .orElse(false);
    }
}
