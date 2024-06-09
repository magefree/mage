package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DescendCondition;
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
 * @author TheElk801
 */
public final class FrilledCaveWurm extends CardImpl {

    public FrilledCaveWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Descend 4 -- Frilled Cave-Wurm gets +2/+0 as long as there are four or more permanent cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield), DescendCondition.FOUR,
                "{this} gets +2/+0 as long as there are four or more permanent cards in your graveyard"
        )).setAbilityWord(AbilityWord.DESCEND_4).addHint(DescendCondition.getHint()));
    }

    private FrilledCaveWurm(final FrilledCaveWurm card) {
        super(card);
    }

    @Override
    public FrilledCaveWurm copy() {
        return new FrilledCaveWurm(this);
    }
}
