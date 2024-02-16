
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author jeffwadsworth
 */
public final class CurseOfWizardry extends CardImpl {

    public CurseOfWizardry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // As Curse of Wizardry enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever a player casts a spell of the chosen color, that player loses 1 life.
        this.addAbility(new CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility());

    }

    private CurseOfWizardry(final CurseOfWizardry card) {
        super(card);
    }

    @Override
    public CurseOfWizardry copy() {
        return new CurseOfWizardry(this);
    }
}

class CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
    }

    private CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(final CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility copy() {
        return new CurseOfWizardryPlayerCastsSpellChosenColorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent curseOfWizardry = game.getPermanent(getSourceId());
        if (curseOfWizardry != null) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(curseOfWizardry.getId() + "_color");
            if (chosenColor != null) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.getColor(game).shares(chosenColor)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell of the chosen color, that player loses 1 life.";
    }
}
