package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoulderbranchGolem extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public BoulderbranchGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Prototype {3}{G} -- 3/3
        this.addAbility(new PrototypeAbility(this, "{3}{G}", 3, 3));

        // When Boulderbranch Golem enters the battlefield, you gain life equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(xValue).setText("you gain life equal to its power")
        ));
    }

    private BoulderbranchGolem(final BoulderbranchGolem card) {
        super(card);
    }

    @Override
    public BoulderbranchGolem copy() {
        return new BoulderbranchGolem(this);
    }
}
