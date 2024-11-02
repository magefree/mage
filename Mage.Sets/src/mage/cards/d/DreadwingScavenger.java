package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class DreadwingScavenger extends CardImpl {

    public DreadwingScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature enters or attacks, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect()));

        // Threshold -- This creature gets +1/+1 and has deathtouch as long as there are seven or more cards in your graveyard.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "This creature gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                ThresholdCondition.instance, "and has deathtouch"
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private DreadwingScavenger(final DreadwingScavenger card) {
        super(card);
    }

    @Override
    public DreadwingScavenger copy() {
        return new DreadwingScavenger(this);
    }
}
