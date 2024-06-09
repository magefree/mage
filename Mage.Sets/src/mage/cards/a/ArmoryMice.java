package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmoryMice extends CardImpl {

    public ArmoryMice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Celebration -- Armory Mice gets +0/+2 as long as two or more nonland permanents entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
                CelebrationCondition.instance, "{this} gets +0/+2 as long as two or more " +
                "nonland permanents entered the battlefield under your control this turn"
        )).addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION), new CelebrationWatcher());
    }

    private ArmoryMice(final ArmoryMice card) {
        super(card);
    }

    @Override
    public ArmoryMice copy() {
        return new ArmoryMice(this);
    }
}
