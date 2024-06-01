package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurnaceHellkite extends CardImpl {

    public FurnaceHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Furnace Hellkite gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")
        ));
    }

    private FurnaceHellkite(final FurnaceHellkite card) {
        super(card);
    }

    @Override
    public FurnaceHellkite copy() {
        return new FurnaceHellkite(this);
    }
}
