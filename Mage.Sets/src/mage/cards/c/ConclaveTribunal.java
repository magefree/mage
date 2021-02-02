package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ConclaveTribunal extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ConclaveTribunal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Conclave Tribunal enters the battlefield, exile target nonland permanent an opponent controls until Conclave Tribunal leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileUntilSourceLeavesEffect(filter.getMessage())
        );
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new OnLeaveReturnExiledToBattlefieldAbility())
        );
        this.addAbility(ability);
    }

    private ConclaveTribunal(final ConclaveTribunal card) {
        super(card);
    }

    @Override
    public ConclaveTribunal copy() {
        return new ConclaveTribunal(this);
    }
}
