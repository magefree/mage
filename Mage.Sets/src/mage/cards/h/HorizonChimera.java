
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HorizonChimera extends CardImpl {

    public HorizonChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever you draw a card, you gain 1 life.
        this.addAbility(new DrawCardControllerTriggeredAbility(new GainLifeEffect(1), false));
    }

    private HorizonChimera(final HorizonChimera card) {
        super(card);
    }

    @Override
    public HorizonChimera copy() {
        return new HorizonChimera(this);
    }
}
