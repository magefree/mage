
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BasilicaScreecher extends CardImpl {

    public BasilicaScreecher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.BAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());
    }

    private BasilicaScreecher(final BasilicaScreecher card) {
        super(card);
    }

    @Override
    public BasilicaScreecher copy() {
        return new BasilicaScreecher(this);
    }
}
