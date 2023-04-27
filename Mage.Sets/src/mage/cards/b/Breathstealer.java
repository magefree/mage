
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class Breathstealer extends CardImpl {

    public Breathstealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}: Breathstealer gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private Breathstealer(final Breathstealer card) {
        super(card);
    }

    @Override
    public Breathstealer copy() {
        return new Breathstealer(this);
    }
}
