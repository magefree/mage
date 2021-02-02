
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CanBlockAsThoughtItHadShadowEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class WallOfDiffusion extends CardImpl {

    public WallOfDiffusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Wall of Diffusion can block creatures with shadow as though Wall of Diffusion had shadow.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAsThoughtItHadShadowEffect(Duration.WhileOnBattlefield)));
    }

    private WallOfDiffusion(final WallOfDiffusion card) {
        super(card);
    }

    @Override
    public WallOfDiffusion copy() {
        return new WallOfDiffusion(this);
    }
}