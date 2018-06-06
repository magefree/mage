
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class FertileGround extends CardImpl {

    public FertileGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new FertileGroundTriggeredAbility());
    }

    public FertileGround(final FertileGround card) {
        super(card);
    }

    @Override
    public FertileGround copy() {
        return new FertileGround(this);
    }
}

class FertileGroundTriggeredAbility extends TriggeredManaAbility {

    public FertileGroundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaAnyColorAttachedControllerEffect());
    }

    public FertileGroundTriggeredAbility(FertileGroundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                getEffects().get(0).setTargetPointer(new FixedTarget(enchanted.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public FertileGroundTriggeredAbility copy() {
        return new FertileGroundTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds an additional one mana of any color.";
    }
}
