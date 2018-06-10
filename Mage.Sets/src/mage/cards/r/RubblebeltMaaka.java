

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */


public final class RubblebeltMaaka extends CardImpl {

    public RubblebeltMaaka (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodrush — {R}, Discard Rubblebelt Maaka: Target attacking creature gets +3/+3 until end of turn.
        this.addAbility(new BloodrushAbility("{R}", new BoostTargetEffect(3,3, Duration.EndOfTurn)));

    }

    public RubblebeltMaaka (final RubblebeltMaaka card) {
        super(card);
    }

    @Override
    public RubblebeltMaaka copy() {
        return new RubblebeltMaaka(this);
    }

}
