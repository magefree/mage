
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class KillerWhale extends CardImpl {

    public KillerWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {U}: Killer Whale gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                    Zone.BATTLEFIELD, 
                    new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), 
                    new ManaCostsImpl<>("{U}")));
    }

    private KillerWhale(final KillerWhale card) {
        super(card);
    }

    @Override
    public KillerWhale copy() {
        return new KillerWhale(this);
    }
}
