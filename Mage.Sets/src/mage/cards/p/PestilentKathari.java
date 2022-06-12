

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class PestilentKathari extends CardImpl {

    public PestilentKathari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);        

        this.power = new MageInt(1);
    this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")));
    }

    private PestilentKathari(final PestilentKathari card) {
        super(card);
    }

    @Override
    public PestilentKathari copy() {
        return new PestilentKathari(this);
    }

}
