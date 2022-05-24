
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MawOfKozilek extends CardImpl {

    public MawOfKozilek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {C}: Maw of Kozilek gets +2/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{C}")));
    }

    private MawOfKozilek(final MawOfKozilek card) {
        super(card);
    }

    @Override
    public MawOfKozilek copy() {
        return new MawOfKozilek(this);
    }
}
