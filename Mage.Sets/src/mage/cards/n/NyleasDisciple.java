package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NyleasDisciple extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.G);

    public NyleasDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Nylea's Disciple enters the battlefield, you gain life equal to your devotion to green.
        Effect effect = new GainLifeEffect(xValue);
        effect.setText("you gain life equal to your devotion to green");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect).addHint(new ValueHint("Devotion to green", xValue)));
    }

    public NyleasDisciple(final NyleasDisciple card) {
        super(card);
    }

    @Override
    public NyleasDisciple copy() {
        return new NyleasDisciple(this);
    }
}
