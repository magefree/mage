
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class MarkOfSakiko extends CardImpl {

    public MarkOfSakiko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature deals combat damage to a player, add that much {G}. Until end of turn, you don't lose this mana as steps and phases end."
        Effect effect = new GainAbilityAttachedEffect(new MarkOfSakikoTriggeredAbility(), AttachmentType.AURA);
        effect.setText("Enchanted creature has \"Whenever this creature deals combat damage to a player, add that much {G}. Until end of turn, you don't lose this mana as steps and phases end.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private MarkOfSakiko(final MarkOfSakiko card) {
        super(card);
    }

    @Override
    public MarkOfSakiko copy() {
        return new MarkOfSakiko(this);
    }
}

class MarkOfSakikoTriggeredAbility extends TriggeredAbilityImpl {

    public MarkOfSakikoTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public MarkOfSakikoTriggeredAbility(final MarkOfSakikoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarkOfSakikoTriggeredAbility copy() {
        return new MarkOfSakikoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedEvent) event).isCombatDamage()) {
            if (event.getSourceId().equals(getSourceId())) {
                this.getEffects().clear();
                Effect effect = new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(event.getAmount()), "that player", true);
                effect.setTargetPointer(new FixedTarget(getControllerId()));
                effect.setText("add that much {G}. Until end of turn, you don't lose this mana as steps and phases end");
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player, add that much {G}. "
                + "Until end of turn, you don't lose this mana as steps and phases end.";
    }
}
