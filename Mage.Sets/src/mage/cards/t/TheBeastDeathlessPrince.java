package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class TheBeastDeathlessPrince extends CardImpl {

    public TheBeastDeathlessPrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When you cast this spell, gain control of target creature until end of turn. Untap it. It gains menace and haste until end of turn.
        TriggeredAbility triggeredAbility = new CastSourceTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn));
        triggeredAbility.addEffect(new UntapTargetEffect("untap it"));
        triggeredAbility.addEffect(new GainAbilityTargetEffect(new MenaceAbility(false), Duration.EndOfTurn).setText("it gains menace"));
        triggeredAbility.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("and haste until end of turn"));
        triggeredAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(triggeredAbility);

        // The Beast enters the battlefield tapped with six stun counters on it.
        Ability etbAbility = new EntersBattlefieldAbility(new TapSourceEffect(true), "tapped with six stun counters on it");
        etbAbility.addEffect(new AddCountersSourceEffect(CounterType.STUN.createInstance(6)));
        this.addAbility(etbAbility);

        // Whenever a creature deals combat damage to its owner, untap The Beast and draw a card.
        this.addAbility(new TheBeastDeathlessPrinceTriggeredAbility());
    }

    private TheBeastDeathlessPrince(final TheBeastDeathlessPrince card) {
        super(card);
    }

    @Override
    public TheBeastDeathlessPrince copy() {
        return new TheBeastDeathlessPrince(this);
    }
}

class TheBeastDeathlessPrinceTriggeredAbility extends TriggeredAbilityImpl {

    public TheBeastDeathlessPrinceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapSourceEffect());
        this.addEffect(new DrawCardSourceControllerEffect(1));
    }

    private TheBeastDeathlessPrinceTriggeredAbility(final TheBeastDeathlessPrinceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());

        return sourcePermanent != null
                && damageEvent.isCombatDamage()
                && damageEvent.getPlayerId().equals(sourcePermanent.getOwnerId());
    }

    @Override
    public TheBeastDeathlessPrinceTriggeredAbility copy() {
        return new TheBeastDeathlessPrinceTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to its owner, untap {this} and draw a card.";
    }
}
