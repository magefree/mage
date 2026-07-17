package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GeneralTraagHeartOfStone extends CardImpl {

    public GeneralTraagHeartOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When General Traag enters, you may sacrifice another artifact. When you do, General Traag deals 4 damage to target creature.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(4), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            ability, new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT),
            "Sacrifice another artifact?"
        )));
    }

    private GeneralTraagHeartOfStone(final GeneralTraagHeartOfStone card) {
        super(card);
    }

    @Override
    public GeneralTraagHeartOfStone copy() {
        return new GeneralTraagHeartOfStone(this);
    }
}
