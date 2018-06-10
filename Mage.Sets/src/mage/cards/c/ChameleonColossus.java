
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ChameleonColossus extends CardImpl {

    public ChameleonColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Changeling (This card is every creature type at all times.)
        this.addAbility(ChangelingAbility.getInstance());

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // {2}{G}{G}: Chameleon Colossus gets +X/+X until end of turn, where X is its power.
        SourcePermanentPowerCount x = new SourcePermanentPowerCount();
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(x, x, Duration.EndOfTurn, true), new ManaCostsImpl("{2}{G}{G}")));
    }

    public ChameleonColossus(final ChameleonColossus card) {
        super(card);
    }

    @Override
    public ChameleonColossus copy() {
        return new ChameleonColossus(this);
    }
}
