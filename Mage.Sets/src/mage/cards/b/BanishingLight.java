
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class BanishingLight extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BanishingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Banishing Light enters the battlefield, exile target nonland permanent an opponent controls until Banishing Light leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledAbility()));
        this.addAbility(ability);
    }

    public BanishingLight(final BanishingLight card) {
        super(card);
    }

    @Override
    public BanishingLight copy() {
        return new BanishingLight(this);
    }
}
