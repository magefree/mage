package mage.cards.v;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfCourage extends AdventureCard {

    public VirtueOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.INSTANT}, "{3}{R}{R}", "Embereth Blaze", "{1}{R}");

        // Whenever a source you control deals noncombat damage to an opponent, you may exile that many cards from the top of your library. You may play those cards this turn.
        this.addAbility(new VirtueOfCourageTriggeredAbility());

        // Embereth Blaze
        // Embereth Blaze deals 2 damage to any target.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTarget());

        this.finalizeAdventure();
    }

    private VirtueOfCourage(final VirtueOfCourage card) {
        super(card);
    }

    @Override
    public VirtueOfCourage copy() {
        return new VirtueOfCourage(this);
    }
}

class VirtueOfCourageTriggeredAbility extends TriggeredAbilityImpl {

    VirtueOfCourageTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    private VirtueOfCourageTriggeredAbility(final VirtueOfCourageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
        if (dEvent.isCombatDamage()
                || !game.getOpponents(getControllerId()).contains(event.getTargetId())
                || !game.getControllerId(event.getSourceId()).equals(getControllerId())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(event.getAmount()));
        return true;
    }

    @Override
    public VirtueOfCourageTriggeredAbility copy() {
        return new VirtueOfCourageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "whenever a source you control deals noncombat damage to an opponent, "
                + "you may exile that many cards from the top of your library. "
                + "You may play those cards this turn.";
    }
}
