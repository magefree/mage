
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class FatalMutation extends CardImpl {

    public FatalMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When enchanted creature is turned face up, destroy it. It can't be regenerated.
        this.addAbility(new FatalMutationAbility(new DestroyAttachedToEffect("it", true)));
    }

    private FatalMutation(final FatalMutation card) {
        super(card);
    }

    @Override
    public FatalMutation copy() {
        return new FatalMutation(this);
    }
}

class FatalMutationAbility extends TriggeredAbilityImpl {

    public FatalMutationAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever enchanted creature is turned face up, ");
    }

    public FatalMutationAbility(final FatalMutationAbility ability) {
        super(ability);
    }

    @Override
    public FatalMutationAbility copy() {
        return new FatalMutationAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachment = game.getPermanent(this.getSourceId());
        if(attachment != null && event.getTargetId().equals(attachment.getAttachedTo())) {
            return true;
        }
        return false;

    }
}

