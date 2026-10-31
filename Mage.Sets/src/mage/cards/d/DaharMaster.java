package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author muz
 */
public final class DaharMaster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public DaharMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When this creature enters, you may sacrifice another nonland permanent. When you do, this creature deals 2 damage to any target.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(2), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            ability, new SacrificeTargetCost(filter), "Sacrifice another nonland permanent?"
        )));
    }

    private DaharMaster(final DaharMaster card) {
        super(card);
    }

    @Override
    public DaharMaster copy() {
        return new DaharMaster(this);
    }
}
