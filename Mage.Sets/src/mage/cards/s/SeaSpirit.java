
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class SeaSpirit extends CardImpl {

    public SeaSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {U}: Sea Spirit gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(+1, +0, Duration.EndOfTurn), new ManaCostsImpl<>("{U}"));
        this.addAbility(ability);
    }

    private SeaSpirit(final SeaSpirit card) {
        super(card);
    }

    @Override
    public SeaSpirit copy() {
        return new SeaSpirit(this);
    }
}
