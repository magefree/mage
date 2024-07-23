package mage.cards.a;

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
 * @author LoneFox
 */
public final class AnuridBarkripper extends CardImpl {

    public AnuridBarkripper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Threshold - Anurid Barkripper gets +2/+2 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "as long as seven or more cards are in your graveyard, {this} gets +2/+2"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private AnuridBarkripper(final AnuridBarkripper card) {
        super(card);
    }

    @Override
    public AnuridBarkripper copy() {
        return new AnuridBarkripper(this);
    }
}
