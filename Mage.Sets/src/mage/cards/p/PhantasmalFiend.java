
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class PhantasmalFiend extends CardImpl {

    public PhantasmalFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // {B}: Phantasmal Fiend gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));

        // {1}{U}: Switch Phantasmal Fiend's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")));
    }

    private PhantasmalFiend(final PhantasmalFiend card) {
        super(card);
    }

    @Override
    public PhantasmalFiend copy() {
        return new PhantasmalFiend(this);
    }
}
