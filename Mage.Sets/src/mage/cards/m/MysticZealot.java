package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class MysticZealot extends CardImpl {

    public MysticZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.MYSTIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Threshold - As long as seven or more cards are in your graveyard, Mystic Zealot gets +1/+1 and has flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "As long as seven or more cards are in your graveyard, {this} gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                ThresholdCondition.instance, "and has flying"
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private MysticZealot(final MysticZealot card) {
        super(card);
    }

    @Override
    public MysticZealot copy() {
        return new MysticZealot(this);
    }
}
