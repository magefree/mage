

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class DarklitGargoyle extends CardImpl {

    public DarklitGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}");

        this.subtype.add(SubType.GARGOYLE);
    this.power = new MageInt(1);
    this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, -1, Duration.EndOfTurn), new ManaCostsImpl<>(("{B}"))));
    }

    private DarklitGargoyle(final DarklitGargoyle card) {
        super(card);
    }

    @Override
    public DarklitGargoyle copy() {
        return new DarklitGargoyle(this);
    }

}
