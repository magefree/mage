package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerrorBallista extends CardImpl {

    public TerrorBallista(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Terror Ballista attacks, you may sacrifice another creature. When you do, destroy target creature an opponent controls.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(
                new DoWhenCostPaid(ability, new SacrificeTargetCost(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
                ), "Sacrifice a creature?")
        ));

        // Unearth {3}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private TerrorBallista(final TerrorBallista card) {
        super(card);
    }

    @Override
    public TerrorBallista copy() {
        return new TerrorBallista(this);
    }
}
