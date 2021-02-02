
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class RazorgrassScreen extends CardImpl {

    public RazorgrassScreen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Razorgrass Screen blocks each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield)));
    }

    private RazorgrassScreen(final RazorgrassScreen card) {
        super(card);
    }

    @Override
    public RazorgrassScreen copy() {
        return new RazorgrassScreen(this);
    }
}
