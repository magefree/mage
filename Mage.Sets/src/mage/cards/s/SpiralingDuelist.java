package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author North
 */
public final class SpiralingDuelist extends CardImpl {

    private static final String effectText = "{this} has double strike as long as you control three or more artifacts.";

    public SpiralingDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Metalcraft — Spiraling Duelist has double strike as long as you control three or more artifacts.
        ContinuousEffect effect = new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, MetalcraftCondition.instance, effectText))
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance));
    }

    private SpiralingDuelist(final SpiralingDuelist card) {
        super(card);
    }

    @Override
    public SpiralingDuelist copy() {
        return new SpiralingDuelist(this);
    }
}
