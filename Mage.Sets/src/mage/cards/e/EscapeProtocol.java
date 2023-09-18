package mage.cards.e;

import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeProtocol extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("artifact or creature you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public EscapeProtocol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cycle a card, you may pay {1}. When you do, exile target artifact or creature you control, then return it to the battlefield under its owner's control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ExileThenReturnTargetEffect(false, false), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new CycleControllerTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1), "Pay {1}?"
        )));
    }

    private EscapeProtocol(final EscapeProtocol card) {
        super(card);
    }

    @Override
    public EscapeProtocol copy() {
        return new EscapeProtocol(this);
    }
}
