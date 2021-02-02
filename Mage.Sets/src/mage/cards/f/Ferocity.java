package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class Ferocity extends CardImpl {

    public Ferocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature blocks or becomes blocked, you may put a +1/+1 counter on it.
        this.addAbility(new FerocityTriggeredAbility());
    }

    private Ferocity(final Ferocity card) {
        super(card);
    }

    @Override
    public Ferocity copy() {
        return new Ferocity(this);
    }
}

class FerocityTriggeredAbility extends TriggeredAbilityImpl {

    public FerocityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "it"), true);
    }

    public FerocityTriggeredAbility(final FerocityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = game.getPermanent(sourceId);
        if (aura == null || aura.getAttachedTo() == null) {
            return false;
        }
        if (event.getSourceId().equals(aura.getAttachedTo())) {
            Permanent blocks = game.getPermanent(event.getTargetId());
            return blocks != null;
        }
        if (event.getTargetId().equals(aura.getAttachedTo())) {
            Permanent blockedBy = game.getPermanent(event.getSourceId());
            return blockedBy != null;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature blocks or becomes blocked, "
                + "you may put a +1/+1 counter on it";
    }

    @Override
    public FerocityTriggeredAbility copy() {
        return new FerocityTriggeredAbility(this);
    }
}
