
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class SwoopingTalon extends CardImpl {

    public SwoopingTalon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}: Swooping Talon loses flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilitySourceEffect(
            FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}")));
        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private SwoopingTalon(final SwoopingTalon card) {
        super(card);
    }

    @Override
    public SwoopingTalon copy() {
        return new SwoopingTalon(this);
    }
}
