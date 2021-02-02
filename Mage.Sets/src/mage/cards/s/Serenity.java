
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Quercitron
 */
public final class Serenity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and enchantments");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }
    
    public Serenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // At the beginning of your upkeep, destroy all artifacts and enchantments. They can't be regenerated.
        Effect effect = new DestroyAllEffect(new FilterPermanent(filter), true);
        Ability ability = new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", effect, false);
        this.addAbility(ability);
    }

    private Serenity(final Serenity card) {
        super(card);
    }

    @Override
    public Serenity copy() {
        return new Serenity(this);
    }
}
