package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class AmbulatoryEdifice extends CardImpl {

    public AmbulatoryEdifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ambulatory Edifice enters the battlefield, you may pay 2 life. When you do, target creature gets -1/-1 until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(-1, -1), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(ability, new PayLifeCost(2), "Pay 2 life?")
        ));
    }

    private AmbulatoryEdifice(final AmbulatoryEdifice card) {
        super(card);
    }

    @Override
    public AmbulatoryEdifice copy() {
        return new AmbulatoryEdifice(this);
    }
}
