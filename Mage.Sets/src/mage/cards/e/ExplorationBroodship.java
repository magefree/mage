package mage.cards.e;

import mage.abilities.common.CastFromGraveyardOnceDuringEachOfYourTurnAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplorationBroodship extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a permanent spell");

    public ExplorationBroodship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 3+
        // You may play an additional land on each of your turns.
        this.addAbility(new StationLevelAbility(3).withLevelAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        )));

        // STATION 8+
        // Flying
        // Once during each of your turns, you may cast a permanent spell from your graveyard by sacrificing a land in addition to paying its other costs.
        // 4/4
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new CastFromGraveyardOnceDuringEachOfYourTurnAbility(
                        filter, new SacrificeTargetCost(StaticFilters.FILTER_LAND).setText("sacrificing a land")
                ))
                .withPT(4, 4));
    }

    private ExplorationBroodship(final ExplorationBroodship card) {
        super(card);
    }

    @Override
    public ExplorationBroodship copy() {
        return new ExplorationBroodship(this);
    }
}
