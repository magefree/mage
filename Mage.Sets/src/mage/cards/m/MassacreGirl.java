package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassacreGirl extends CardImpl {

    public MassacreGirl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Massacre Girl enters the battlefield, each other creature gets -1/-1 until end of turn. Whenever a creature dies this turn, each creature other than Massacre Girl gets -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MassacreGirlEffect()));
    }

    private MassacreGirl(final MassacreGirl card) {
        super(card);
    }

    @Override
    public MassacreGirl copy() {
        return new MassacreGirl(this);
    }
}

class MassacreGirlEffect extends OneShotEffect {

    MassacreGirlEffect() {
        super(Outcome.Benefit);
        staticText = "each other creature gets -1/-1 until end of turn. " +
                "Whenever a creature dies this turn, " +
                "each creature other than {this} gets -1/-1 until end of turn.";
    }

    private MassacreGirlEffect(final MassacreGirlEffect effect) {
        super(effect);
    }

    @Override
    public MassacreGirlEffect copy() {
        return new MassacreGirlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, true), source);
        game.addDelayedTriggeredAbility(new MassacreGirlDelayedTriggeredAbility(), source);
        return true;
    }
}

class MassacreGirlDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MassacreGirlDelayedTriggeredAbility() {
        super(new BoostAllEffect(-1, -1, Duration.EndOfTurn, true), Duration.EndOfTurn, false);
    }

    private MassacreGirlDelayedTriggeredAbility(final MassacreGirlDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && zEvent.getTarget().isCreature(game);
    }

    @Override
    public MassacreGirlDelayedTriggeredAbility copy() {
        return new MassacreGirlDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dies this turn, each creature other than {this} gets -1/-1 until end of turn";
    }
}