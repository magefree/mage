package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CanBlockAsThoughtItHadShadowEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author noahg
 */
public final class AetherflameWall extends CardImpl {

    public AetherflameWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Aetherflame Wall can block creatures with shadow as though they didn’t have shadow.
        this.addAbility(new SimpleStaticAbility(new CanBlockAsThoughtItHadShadowEffect(Duration.WhileOnBattlefield)
                .setText("{this} can block creatures with shadow as though they didn't have shadow")));

        // {R}: Aetherflame Wall gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")
        ));
    }

    private AetherflameWall(final AetherflameWall card) {
        super(card);
    }

    @Override
    public AetherflameWall copy() {
        return new AetherflameWall(this);
    }
}
