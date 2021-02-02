
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MetamorphicWurm extends CardImpl {

    public MetamorphicWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - Metamorphic Wurm gets +4/+4 as long as seven or more cards are in your graveyard.
        Ability thresholdAbility = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield),
                    new CardsInControllerGraveyardCondition(7),
                    "{this} gets +4/+4 as long as seven or more cards are in your graveyard"
                ));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private MetamorphicWurm(final MetamorphicWurm card) {
        super(card);
    }

    @Override
    public MetamorphicWurm copy() {
        return new MetamorphicWurm(this);
    }
}
