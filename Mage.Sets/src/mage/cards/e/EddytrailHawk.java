
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EddytrailHawk extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EddytrailHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Eddytail Hawk enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));
        // When Eddytail Hawk attacks you pay {E}. If you do, another target attacking creature gains flying until end of turn.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new PayEnergyCost(1), null, true);
        Ability ability = new AttacksTriggeredAbility(doIfCostPaidEffect, false,
                "Whenever {this} attacks you pay {E}. If you do, another target attacking creature gains flying until end of turn.");
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private EddytrailHawk(final EddytrailHawk card) {
        super(card);
    }

    @Override
    public EddytrailHawk copy() {
        return new EddytrailHawk(this);
    }
}
