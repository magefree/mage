package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ChromaCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HeartlashCinder extends CardImpl {

    public HeartlashCinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Chroma - When Heartlash Cinder enters the battlefield, it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control.
        DynamicValue xValue = new ChromaCount(ManaType.RED);
        ContinuousEffect effect = new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true);
        effect.setText("it gets +X/+0 until end of turn, where X is the number of red mana symbols in the mana costs of permanents you control");
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                effect, false)
                .setAbilityWord(AbilityWord.CHROMA)
                .addHint(new ValueHint("Red mana symbols in your permanents", xValue))
        );
    }

    private HeartlashCinder(final HeartlashCinder card) {
        super(card);
    }

    @Override
    public HeartlashCinder copy() {
        return new HeartlashCinder(this);
    }
}
