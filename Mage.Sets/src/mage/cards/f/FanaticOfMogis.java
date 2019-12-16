package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FanaticOfMogis extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.R);

    public FanaticOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Fanatic of Mogis enters the battlefield, it deals damage to each opponent equal to your devotion to red.
        Effect effect = new DamagePlayersEffect(Outcome.Damage, xValue, TargetController.OPPONENT);
        effect.setText("it deals damage to each opponent equal to your devotion to red. (Each {R} in the mana costs of permanents you control counts towards your devotion to red.)");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false).addHint(new ValueHint("Devotion to red", xValue)));
    }

    public FanaticOfMogis(final FanaticOfMogis card) {
        super(card);
    }

    @Override
    public FanaticOfMogis copy() {
        return new FanaticOfMogis(this);
    }
}
