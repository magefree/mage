
package mage.cards.g;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.events.GameEvent;

/**
 *
 * @author jonubuu
 */
public final class Glimmervoid extends CardImpl {

    public Glimmervoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // At the beginning of the end step, if you control no artifacts, sacrifice Glimmervoid.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent(), ComparisonType.FEWER_THAN, 1),
                "At the beginning of the end step, if you control no artifacts, sacrifice {this}."));
        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private Glimmervoid(final Glimmervoid card) {
        super(card);
    }

    @Override
    public Glimmervoid copy() {
        return new Glimmervoid(this);
    }
}
