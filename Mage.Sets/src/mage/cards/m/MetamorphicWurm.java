
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MetamorphicWurm extends CardImpl {

    public MetamorphicWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - Metamorphic Wurm gets +4/+4 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "{this} gets +4/+4 as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private MetamorphicWurm(final MetamorphicWurm card) {
        super(card);
    }

    @Override
    public MetamorphicWurm copy() {
        return new MetamorphicWurm(this);
    }
}
