
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ConsulsShieldguard extends CardImpl {

    private final static FilterAttackingCreature filter = new FilterAttackingCreature();

    static {
        filter.add(new AnotherPredicate());
    }

    public ConsulsShieldguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Consul's Shieldguard enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Consul's Shiedguard attacks, you may pay {E}. If you do, another target attacking creature gets indestructible until end of turn.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new PayEnergyCost(1));
        Ability ability = new AttacksTriggeredAbility(doIfCostPaidEffect, true,
                "Whenever {this} attacks, you may pay {E}. If you do, another target attacking creature gets indestructible until end of turn.");
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public ConsulsShieldguard(final ConsulsShieldguard card) {
        super(card);
    }

    @Override
    public ConsulsShieldguard copy() {
        return new ConsulsShieldguard(this);
    }
}
