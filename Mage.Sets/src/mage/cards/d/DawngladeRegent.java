package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawngladeRegent extends CardImpl {

    public DawngladeRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Dawnglade Regent enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // As long as you're the monarch, permanents you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        HexproofAbility.getInstance(), Duration.WhileOnBattlefield
                ), MonarchIsSourceControllerCondition.instance,
                "as long as you're the monarch, permanents you control have hexproof"
        )));
    }

    private DawngladeRegent(final DawngladeRegent card) {
        super(card);
    }

    @Override
    public DawngladeRegent copy() {
        return new DawngladeRegent(this);
    }
}
