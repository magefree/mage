package mage.cards.s;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmokeBomb extends CardImpl {

    public SmokeBomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // All creatures have shroud.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ShroudAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // At the beginning of your upkeep, sacrifice Smoke Bomb. When you do, target creature you control can't be blocked this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBeBlockedTargetEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeSourceCost(), "", false
        ), TargetController.YOU, false));
    }

    private SmokeBomb(final SmokeBomb card) {
        super(card);
    }

    @Override
    public SmokeBomb copy() {
        return new SmokeBomb(this);
    }
}
