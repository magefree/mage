
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MannichiTheFeveredDream extends CardImpl {

    public MannichiTheFeveredDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}{R}: Switch each creature's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwitchPowerToughnessAllEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private MannichiTheFeveredDream(final MannichiTheFeveredDream card) {
        super(card);
    }

    @Override
    public MannichiTheFeveredDream copy() {
        return new MannichiTheFeveredDream(this);
    }
}
