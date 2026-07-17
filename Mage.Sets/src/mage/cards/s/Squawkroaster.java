package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Squawkroaster extends CardImpl {

    public Squawkroaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vivid -- Squawkroaster's power is equal to the number of colors among permanents you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
                .setText("{this}'s power is equal to the number of colors among permanents you control")
        ).setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private Squawkroaster(final Squawkroaster card) {
        super(card);
    }

    @Override
    public Squawkroaster copy() {
        return new Squawkroaster(this);
    }
}
