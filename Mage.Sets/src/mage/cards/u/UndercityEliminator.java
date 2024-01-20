package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
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
public final class UndercityEliminator extends CardImpl {

    public UndercityEliminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Undercity Eliminator enters the battlefield, you may sacrifice an artifact or creature. When you do, exile target creature an opponent controls.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ExileTargetEffect(), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT),
                "Sacrifice an artifact or creature?"
        )));
    }

    private UndercityEliminator(final UndercityEliminator card) {
        super(card);
    }

    @Override
    public UndercityEliminator copy() {
        return new UndercityEliminator(this);
    }
}
