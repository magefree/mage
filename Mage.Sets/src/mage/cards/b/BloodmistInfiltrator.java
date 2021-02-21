package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodmistInfiltrator extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BloodmistInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Bloodmist Infiltrator attacks, you may sacrifice another creature. If you do, Bloodmist Infiltrator can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ), false));
    }

    private BloodmistInfiltrator(final BloodmistInfiltrator card) {
        super(card);
    }

    @Override
    public BloodmistInfiltrator copy() {
        return new BloodmistInfiltrator(this);
    }
}
