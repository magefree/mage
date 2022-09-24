
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public final class IsolationZone extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment an opponent controls");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public IsolationZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // When Isolation Zone enters the battlefield, exile target creature or enchantment an opponent controls until Isolation Zone leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private IsolationZone(final IsolationZone card) {
        super(card);
    }

    @Override
    public IsolationZone copy() {
        return new IsolationZone(this);
    }
}
