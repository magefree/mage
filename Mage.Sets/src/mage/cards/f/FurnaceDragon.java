package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FurnaceDragon extends CardImpl {

    public FurnaceDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Furnace Dragon enters the battlefield, if you cast it from your hand, exile all artifacts.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ExileAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS)
        ).withInterveningIf(CastFromHandSourcePermanentCondition.instance), new CastFromHandWatcher());
    }

    private FurnaceDragon(final FurnaceDragon card) {
        super(card);
    }

    @Override
    public FurnaceDragon copy() {
        return new FurnaceDragon(this);
    }
}
