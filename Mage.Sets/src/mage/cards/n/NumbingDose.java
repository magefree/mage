
package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class NumbingDose extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public NumbingDose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted permanent doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect("permanent")));

        // At the beginning of the upkeep of enchanted permanent's controller, that player loses 1 life.
        this.addAbility(new NumbingDoseTriggeredAbility());
    }

    private NumbingDose(final NumbingDose card) {
        super(card);
    }

    @Override
    public NumbingDose copy() {
        return new NumbingDose(this);
    }
}

class NumbingDoseTriggeredAbility extends TriggeredAbilityImpl {

    public NumbingDoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
    }

    public NumbingDoseTriggeredAbility(final NumbingDoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NumbingDoseTriggeredAbility copy() {
        return new NumbingDoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment != null) {
            Permanent permanent = game.getPermanent(equipment.getAttachedTo());
            if (permanent != null && event.getPlayerId().equals(permanent.getControllerId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of the upkeep of enchanted permanent's controller, that player loses 1 life.";
    }
}
