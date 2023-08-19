package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public final class PrismRing extends CardImpl {

    public PrismRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // As Prism Ring enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever you cast a spell of the chosen color, you gain 1 life.
        this.addAbility(new PrismRingTriggeredAbility());
    }

    private PrismRing(final PrismRing card) {
        super(card);
    }

    @Override
    public PrismRing copy() {
        return new PrismRing(this);
    }
}

class PrismRingTriggeredAbility extends TriggeredAbilityImpl {

    public PrismRingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
    }

    public PrismRingTriggeredAbility(final PrismRingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (spell != null && color != null && spell.getColor(game).shares(color)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell of the chosen color, you gain 1 life.";
    }

    @Override
    public PrismRingTriggeredAbility copy() {
        return new PrismRingTriggeredAbility(this);
    }
}
