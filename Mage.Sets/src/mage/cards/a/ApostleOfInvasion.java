package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ApostleOfInvasion extends CardImpl {

    public ApostleOfInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Corrupted -- As long as an opponent has three or more poison counters, Apostle of Invasion has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()), CorruptedCondition.instance,
                "as long as an opponent has three or more poison counters, {this} has double strike"
        )).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private ApostleOfInvasion(final ApostleOfInvasion card) {
        super(card);
    }

    @Override
    public ApostleOfInvasion copy() {
        return new ApostleOfInvasion(this);
    }
}
