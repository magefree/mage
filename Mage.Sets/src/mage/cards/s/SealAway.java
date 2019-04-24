
package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

/**
 * @author JRHerlehy
 * Created on 4/4/18.
 */
public final class SealAway extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new TappedPredicate());
    }

    public SealAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        //Flash
        this.addAbility(FlashAbility.getInstance());

        //When Seal Away enters the battlefield, exile target tapped creature an opponent controls until Seal Away leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    public SealAway(final SealAway card) {
        super(card);
    }

    @Override
    public SealAway copy() {
        return new SealAway(this);
    }
}
