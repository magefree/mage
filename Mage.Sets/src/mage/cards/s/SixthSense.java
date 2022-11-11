
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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

/**
 *
 * @author fireshoes
 */
public final class SixthSense extends CardImpl {

    public SixthSense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature deals combat damage to a player, you may draw a card."
        Effect effect  = new GainAbilityAttachedEffect(new SixthSenseTriggeredAbility(), AttachmentType.AURA);
        effect.setText("Enchanted creature has \"Whenever this creature deals combat damage to a player, you may draw a card.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private SixthSense(final SixthSense card) {
        super(card);
    }

    @Override
    public SixthSense copy() {
        return new SixthSense(this);
    }
}

class SixthSenseTriggeredAbility extends TriggeredAbilityImpl {

    public SixthSenseTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    public SixthSenseTriggeredAbility(final SixthSenseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SixthSenseTriggeredAbility copy() {
        return new SixthSenseTriggeredAbility(this);
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
                this.addEffect(new DrawCardSourceControllerEffect(1));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player, you may draw a card.";
    }
}
