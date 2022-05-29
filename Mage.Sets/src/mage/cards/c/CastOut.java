
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class CastOut extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public CastOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Cast Out enters the battlefield, exile target nonland permanent an opponent controls until Cast Out leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Cycling {W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{W}")));

    }

    private CastOut(final CastOut card) {
        super(card);
    }

    @Override
    public CastOut copy() {
        return new CastOut(this);
    }
}
