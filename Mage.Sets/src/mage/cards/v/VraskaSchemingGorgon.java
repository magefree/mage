package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VraskaSchemingGorgon extends CardImpl {

    public VraskaSchemingGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);
        this.setStartingLoyalty(5);

        // +2: Creatures you control get +1/+0 until end of turn.
        this.addAbility(new LoyaltyAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn), 2));

        // -3: Destroy target creature.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(loyaltyAbility);

        // -10: Until end of turn, creatures you control gain deathtouch and "Whenever this creature deals damage to an opponent, that player loses the game."
        loyaltyAbility = new LoyaltyAbility(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("Until end of turn, creatures you control gain deathtouch"), -10);
        TriggeredAbility triggeredAbility = new VraskaSchemingGorgonTriggeredAbility(new LoseGameTargetPlayerEffect());
        loyaltyAbility.addEffect(new GainAbilityControlledEffect(triggeredAbility, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("and \"Whenever this creature deals damage to an opponent, that player loses the game.\""));
        this.addAbility(loyaltyAbility);
    }

    private VraskaSchemingGorgon(final VraskaSchemingGorgon card) {
        super(card);
    }

    @Override
    public VraskaSchemingGorgon copy() {
        return new VraskaSchemingGorgon(this);
    }
}

class VraskaSchemingGorgonTriggeredAbility extends TriggeredAbilityImpl {

    VraskaSchemingGorgonTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever this creature deals damage to an opponent, ");
    }

    private VraskaSchemingGorgonTriggeredAbility(final VraskaSchemingGorgonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VraskaSchemingGorgonTriggeredAbility copy() {
        return new VraskaSchemingGorgonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }


}
