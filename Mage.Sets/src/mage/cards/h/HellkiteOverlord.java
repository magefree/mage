

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class HellkiteOverlord extends CardImpl {

    public HellkiteOverlord (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{R}{R}{G}");
        this.subtype.add(SubType.DRAGON);


        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}{G}")));
    }

    public HellkiteOverlord (final HellkiteOverlord card) {
        super(card);
    }

    @Override
    public HellkiteOverlord copy() {
        return new HellkiteOverlord(this);
    }

}
