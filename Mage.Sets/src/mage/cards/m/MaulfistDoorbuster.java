
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MaulfistDoorbuster extends CardImpl {

    public MaulfistDoorbuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Maulfist Doorbuster enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Maulfist Doorbuster attacks, you may pay {E}. If you do, target creature can't block this turn.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new CantBlockTargetEffect(Duration.EndOfTurn), new PayEnergyCost(1));
        Ability ability = new AttacksTriggeredAbility(doIfCostPaidEffect, false,
                "Whenever {this} attacks, you may pay {E}. If you do, target creature can't block this turn.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MaulfistDoorbuster(final MaulfistDoorbuster card) {
        super(card);
    }

    @Override
    public MaulfistDoorbuster copy() {
        return new MaulfistDoorbuster(this);
    }
}
