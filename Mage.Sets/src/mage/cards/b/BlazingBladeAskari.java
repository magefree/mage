
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class BlazingBladeAskari extends CardImpl {

    public BlazingBladeAskari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        // {2}: Blazing Blade Askari becomes colorless until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorSourceEffect(ObjectColor.COLORLESS, Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private BlazingBladeAskari(final BlazingBladeAskari card) {
        super(card);
    }

    @Override
    public BlazingBladeAskari copy() {
        return new BlazingBladeAskari(this);
    }
}
