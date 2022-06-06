
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
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
public final class GhostlyChangeling extends CardImpl {

    public GhostlyChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new ChangelingAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}")));
    }

    private GhostlyChangeling(final GhostlyChangeling card) {
        super(card);
    }

    @Override
    public GhostlyChangeling copy() {
        return new GhostlyChangeling(this);
    }
}
