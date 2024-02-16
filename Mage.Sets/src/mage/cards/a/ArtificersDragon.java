package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtificersDragon extends CardImpl {

    public ArtificersDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Artifact creatures you control get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, false
        ), new ManaCostsImpl<>("{R}")));

        // Unearth {3}{R}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private ArtificersDragon(final ArtificersDragon card) {
        super(card);
    }

    @Override
    public ArtificersDragon copy() {
        return new ArtificersDragon(this);
    }
}
