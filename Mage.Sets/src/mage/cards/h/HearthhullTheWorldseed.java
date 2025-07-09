package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HearthhullTheWorldseed extends CardImpl {

    public HearthhullTheWorldseed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 2+
        // {1}, {T}, Sacrifice a land: Draw two cards. You may play an additional land this turn.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        ability.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.addAbility(new StationLevelAbility(2).withLevelAbility(ability));

        // STATION 8+
        // Flying
        // Vigilance
        // Haste
        // Whenever you sacrifice a land, each opponent loses 2 life.
        // 6/7
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(VigilanceAbility.getInstance())
                .withLevelAbility(HasteAbility.getInstance())
                .withLevelAbility(new SacrificePermanentTriggeredAbility(
                        new LoseLifeOpponentsEffect(2), StaticFilters.FILTER_LAND
                ))
                .withPT(6, 7));
    }

    private HearthhullTheWorldseed(final HearthhullTheWorldseed card) {
        super(card);
    }

    @Override
    public HearthhullTheWorldseed copy() {
        return new HearthhullTheWorldseed(this);
    }
}
