package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NighthawkScavenger extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            CardTypesInGraveyardCount.OPPONENTS, StaticValue.get(1)
    );

    public NighthawkScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Nighthawk Scavenger's power is equal to 1 plus the number of card types among cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(xValue, Duration.EndOfGame)
                .setText("{this}'s power is equal to 1 plus the number of " +
                        "card types among cards in your opponents' graveyards. " +
                        "<i>(Cards in graveyards have only the characteristics of their front face.)</i>")
        ).addHint(CardTypesInGraveyardHint.OPPONENTS));
    }

    private NighthawkScavenger(final NighthawkScavenger card) {
        super(card);
    }

    @Override
    public NighthawkScavenger copy() {
        return new NighthawkScavenger(this);
    }
}
