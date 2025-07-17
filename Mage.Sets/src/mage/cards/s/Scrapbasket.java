
package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class Scrapbasket extends CardImpl {

    public Scrapbasket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}: Scrapbasket becomes all colors until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesColorSourceEffect(new ObjectColor("WUBRG"), Duration.EndOfTurn), new ManaCostsImpl<>("{1}")));
        
    }

    private Scrapbasket(final Scrapbasket card) {
        super(card);
    }

    @Override
    public Scrapbasket copy() {
        return new Scrapbasket(this);
    }
}
