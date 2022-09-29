

package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author nantuko
 */
public final class RelicPutrescence extends CardImpl {

    public RelicPutrescence (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        this.addAbility(new RelicPutrescenceAbility());
    }

    public RelicPutrescence (final RelicPutrescence card) {
        super(card);
    }

    @Override
    public RelicPutrescence copy() {
        return new RelicPutrescence(this);
    }

}

class RelicPutrescenceAbility extends TriggeredAbilityImpl {

    public RelicPutrescenceAbility() {
        super(Zone.BATTLEFIELD, new AddCountersControllerEffect(CounterType.POISON.createInstance(), true));
    }

    public RelicPutrescenceAbility(final RelicPutrescenceAbility ability) {
        super(ability);
    }

    @Override
    public RelicPutrescenceAbility copy() {
        return new RelicPutrescenceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getTargetId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted artifact becomes tapped, its controller gets a poison counter.";
    }

}
