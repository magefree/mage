package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class MysticVisionary extends CardImpl {

    public MysticVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.MYSTIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Threshold - Mystic Visionary has flying as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()), ThresholdCondition.instance,
                "{this} has flying as long as seven or more cards are in your graveyard."
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private MysticVisionary(final MysticVisionary card) {
        super(card);
    }

    @Override
    public MysticVisionary copy() {
        return new MysticVisionary(this);
    }
}
