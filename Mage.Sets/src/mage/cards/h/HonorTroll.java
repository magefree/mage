package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.replacement.GainPlusOneLifeReplacementEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HonorTroll extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 25);

    public HonorTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new GainPlusOneLifeReplacementEffect()));

        // Honor Troll gets +2/+1 as long as you have 25 or more life.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
                condition, "{this} gets +2/+1 as long as you have 25 or more life"
        )));
    }

    private HonorTroll(final HonorTroll card) {
        super(card);
    }

    @Override
    public HonorTroll copy() {
        return new HonorTroll(this);
    }
}
