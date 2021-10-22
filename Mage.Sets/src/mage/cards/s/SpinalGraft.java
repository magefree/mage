package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class SpinalGraft extends CardImpl {

    public SpinalGraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield)));

        // When enchanted creature becomes the target of a spell or ability, destroy that creature. It can't be regenerated.
        this.addAbility(new SpinalGraftTriggeredAbility());
    }

    private SpinalGraft(final SpinalGraft card) {
        super(card);
    }

    @Override
    public SpinalGraft copy() {
        return new SpinalGraft(this);
    }
}

class SpinalGraftTriggeredAbility extends TriggeredAbilityImpl {

    public SpinalGraftTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true));
    }

    public SpinalGraftTriggeredAbility(final SpinalGraftTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpinalGraftTriggeredAbility copy() {
        return new SpinalGraftTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            UUID enchanted = enchantment.getAttachedTo();
            if (event.getTargetId().equals(enchanted)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(enchanted, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature becomes the target of a spell or ability, destroy that creature. It can't be regenerated.";
    }
}
