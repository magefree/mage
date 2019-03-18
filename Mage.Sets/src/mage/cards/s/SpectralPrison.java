
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx
 */
public final class SpectralPrison extends CardImpl {

    public SpectralPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect()));

        // When enchanted creature becomes the target of a spell, sacrifice Spectral Prison.
        this.addAbility(new SpectralPrisonAbility());
    }

    public SpectralPrison(final SpectralPrison card) {
        super(card);
    }

    @Override
    public SpectralPrison copy() {
        return new SpectralPrison(this);
    }
}

class SpectralPrisonAbility extends TriggeredAbilityImpl {

    public SpectralPrisonAbility() {
        super(Zone.BATTLEFIELD, new DestroySourceEffect());
    }

    public SpectralPrisonAbility(final SpectralPrisonAbility ability) {
        super(ability);
    }

    @Override
    public SpectralPrisonAbility copy() {
        return new SpectralPrisonAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (eventSourceObject instanceof Spell) {
            Permanent enchantment = game.getPermanent(sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                if (event.getTargetId().equals(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature becomes the target of a spell or ability, destroy {this}.";
    }

}
