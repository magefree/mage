
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public final class LivingArtifact extends CardImpl {

    public LivingArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Whenever you're dealt damage, put that many vitality counters on Living Artifact.
        this.addAbility(new LivingArtifactTriggeredAbility());
        // At the beginning of your upkeep, you may remove a vitality counter from Living Artifact. If you do, you gain 1 life.
        //TODO make this a custom ability- it's really not intervening if because you should be able to add counters in response to this trigger
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1),
            new RemoveCountersSourceCost(CounterType.VITALITY.createInstance(1))), TargetController.YOU, false),
            new SourceHasCounterCondition(CounterType.VITALITY, 1), "At the beginning of your upkeep, you may remove a vitality counter from {this}. If you do, you gain 1 life"));
    }

    private LivingArtifact(final LivingArtifact card) {
        super(card);
    }

    @Override
    public LivingArtifact copy() {
        return new LivingArtifact(this);
    }
}

class LivingArtifactTriggeredAbility extends TriggeredAbilityImpl {

    public LivingArtifactTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LivingArtifactEffect(), false);
    }

    public LivingArtifactTriggeredAbility(final LivingArtifactTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LivingArtifactTriggeredAbility copy() {
        return new LivingArtifactTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, put that many vitality counters on {this}.";
    }
}

class LivingArtifactEffect extends OneShotEffect {

    public LivingArtifactEffect() {
        super(Outcome.Benefit);
    }

    public LivingArtifactEffect(final LivingArtifactEffect effect) {
        super(effect);
    }

    @Override
    public LivingArtifactEffect copy() {
        return new LivingArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.VITALITY.createInstance((Integer) this.getValue("damageAmount"))).apply(game, source);
    }
}
