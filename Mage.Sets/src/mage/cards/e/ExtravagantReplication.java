package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExtravagantReplication extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("another target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ExtravagantReplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        // At the beginning of your upkeep, create a token that's a copy of another target nonland permanent you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenCopyTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ExtravagantReplication(final ExtravagantReplication card) {
        super(card);
    }

    @Override
    public ExtravagantReplication copy() {
        return new ExtravagantReplication(this);
    }
}
