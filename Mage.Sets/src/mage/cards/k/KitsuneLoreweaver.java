
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class KitsuneLoreweaver extends CardImpl {

    public KitsuneLoreweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{W}: Kitsune Loreweaver gets +0/+X until end of turn, where X is the number of cards in your hand.
        Effect effect = new BoostSourceEffect(StaticValue.get(0), CardsInControllerHandCount.instance, Duration.EndOfTurn, true);
        effect.setText("{this} gets +0/+X until end of turn, where X is the number of cards in your hand");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{W}")));
    }

    private KitsuneLoreweaver(final KitsuneLoreweaver card) {
        super(card);
    }

    @Override
    public KitsuneLoreweaver copy() {
        return new KitsuneLoreweaver(this);
    }
}
