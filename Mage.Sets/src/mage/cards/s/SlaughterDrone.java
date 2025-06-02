
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DevoidAbility;
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
public final class SlaughterDrone extends CardImpl {

    public SlaughterDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // {C}: Slaughter Drone gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{C}")));

    }

    private SlaughterDrone(final SlaughterDrone card) {
        super(card);
    }

    @Override
    public SlaughterDrone copy() {
        return new SlaughterDrone(this);
    }
}
