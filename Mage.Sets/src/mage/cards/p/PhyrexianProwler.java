
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class PhyrexianProwler extends CardImpl {

    public PhyrexianProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Fading 3
        this.addAbility(new FadingAbility(3, this));
        // Remove a fade counter from Phyrexian Prowler: Phyrexian Prowler gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn),
            new RemoveCountersSourceCost(CounterType.FADE.createInstance())));
    }

    private PhyrexianProwler(final PhyrexianProwler card) {
        super(card);
    }

    @Override
    public PhyrexianProwler copy() {
        return new PhyrexianProwler(this);
    }
}
