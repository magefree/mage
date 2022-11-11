

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class WallOfTanglecord extends CardImpl {

    public WallOfTanglecord (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}")));
    }

    public WallOfTanglecord (final WallOfTanglecord card) {
        super(card);
    }

    @Override
    public WallOfTanglecord copy() {
        return new WallOfTanglecord(this);
    }

}
