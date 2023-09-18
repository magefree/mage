
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class LionheartMaverick extends CardImpl {

    public LionheartMaverick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}")));
    }

    private LionheartMaverick(final LionheartMaverick card) {
        super(card);
    }

    @Override
    public LionheartMaverick copy() {
        return new LionheartMaverick(this);
    }
}
