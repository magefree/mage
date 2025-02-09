package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZahurGlorysPast extends CardImpl {

    public ZahurGlorysPast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Sacrifice another creature: Surveil 1. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new SurveilEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE)
        ));

        // Max speed -- Whenever a nontoken creature you control dies, create a tapped 2/2 black Zombie creature token.
        this.addAbility(new MaxSpeedAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new ZombieToken(), 1, true),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        )));
    }

    private ZahurGlorysPast(final ZahurGlorysPast card) {
        super(card);
    }

    @Override
    public ZahurGlorysPast copy() {
        return new ZahurGlorysPast(this);
    }
}
