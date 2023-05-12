package mage.cards.t;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheTabernacleAtPendrellVale extends CardImpl {

    public TheTabernacleAtPendrellVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // All creatures have "At the beginning of your upkeep, destroy this creature unless you pay {1}."
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(new BeginningOfUpkeepTriggeredAbility(
                        new DoIfCostPaid(
                                new InfoEffect(""), new DestroySourceEffect(), new GenericManaCost(1)
                        ).setText("destroy this creature unless you pay {1}"),
                        TargetController.YOU, false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)
        ));
    }

    private TheTabernacleAtPendrellVale(final TheTabernacleAtPendrellVale card) {
        super(card);
    }

    @Override
    public TheTabernacleAtPendrellVale copy() {
        return new TheTabernacleAtPendrellVale(this);
    }
}
