
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ExaltedAngel extends CardImpl {

    public ExaltedAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Exalted Angel deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
        // Morph {2}{W}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{W}{W}")));
    }

    private ExaltedAngel(final ExaltedAngel card) {
        super(card);
    }

    @Override
    public ExaltedAngel copy() {
        return new ExaltedAngel(this);
    }
}
