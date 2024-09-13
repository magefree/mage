package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrievousWound extends CardImpl {

    public GrievousWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted player can't gain life.
        this.addAbility(new SimpleStaticAbility(
                new CantGainLifeAllEffect(Duration.WhileOnBattlefield, TargetController.ENCHANTED)
        ));

        // Whenever enchanted player is dealt damage, they lose half their life, rounded up.
        this.addAbility(new GrievousWoundTriggeredAbility());
    }

    private GrievousWound(final GrievousWound card) {
        super(card);
    }

    @Override
    public GrievousWound copy() {
        return new GrievousWound(this);
    }
}

class GrievousWoundTriggeredAbility extends TriggeredAbilityImpl {

    GrievousWoundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseHalfLifeTargetEffect().setText("they lose half their life, rounded up"));
        this.setTriggerPhrase("Whenever enchanted player is dealt damage, ");
    }

    private GrievousWoundTriggeredAbility(final GrievousWoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrievousWoundTriggeredAbility copy() {
        return new GrievousWoundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachment = getSourcePermanentIfItStillExists(game);
        if (attachment == null || !event.getTargetId().equals(attachment.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}
