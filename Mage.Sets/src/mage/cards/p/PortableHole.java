package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortableHole extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PortableHole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        // When Portable Hole enters the battlefield, exile target nonland permanent an opponent controls with mana value 2 or less until Portable Hole leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileUntilSourceLeavesEffect("")
                        .setText("exile target nonland permanent an opponent controls with mana value 2 " +
                                "or less until {this} leaves the battlefield")
        );
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private PortableHole(final PortableHole card) {
        super(card);
    }

    @Override
    public PortableHole copy() {
        return new PortableHole(this);
    }
}
