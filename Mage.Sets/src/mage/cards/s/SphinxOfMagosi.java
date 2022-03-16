

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class SphinxOfMagosi extends CardImpl {

    public SphinxOfMagosi (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{2}{U}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy(", then"));
        this.addAbility(ability);
    }

    public SphinxOfMagosi (final SphinxOfMagosi card) {
        super(card);
    }

    @Override
    public SphinxOfMagosi copy() {
        return new SphinxOfMagosi(this);
    }

}
